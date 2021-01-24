package com.example.todoapp;

import android.annotation.SuppressLint;

import org.apache.commons.io.FileUtils; //updated this import statement to get readlines to work

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity<music> extends AppCompatActivity {

    List<String> items;


    int nowPlaying = 0;
    int[] trackList = new int[] {R.raw.slope};

    Button btnAdd;
    EditText etItem; //Text box
    RecyclerView itemList;
    ItemsAdapter itemsAdapter;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////AUDIO/////////////
        MediaPlayer music = MediaPlayer.create(this, trackList[nowPlaying]);
        music.start();
        //music.setLooping(true);

        ///////////////LIST//////////////
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        itemList = findViewById(R.id.itemsList);

        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            ///////////DELETING//////////
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item
                items.remove(position);
                //Notify the adapter at which position we deleted an item
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Successfully removed", Toast.LENGTH_SHORT).show();
                System.out.println(items.size());
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        itemList.setAdapter(itemsAdapter);
        itemList.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            ///////////////SAVING///////////////
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //add item to the model
                items.add(todoItem);
                //notify adapter that item was added
                itemsAdapter.notifyItemInserted(items.size() - 1); //add at final index
                etItem.setText(""); //This clears the textbox
                Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    //Now it's time for data persistence
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //This function load items by reading data.txt
    private void loadItems(){
        //Try catch so if something goes wrong the app won't crash!!
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Oops!! Something went wrong!", e);
            items = new ArrayList<>();
        }
    }

    //This function saves items to data.txt
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Oops!! Something went wrong!", e);
            items = new ArrayList<>();
        }
    }





}
