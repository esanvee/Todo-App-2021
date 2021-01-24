package com.example.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//takes data and puts into view holder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.Viewholder> {


    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items; //here are our items
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener onLongClickListener) {
        //Initializeing the items list and long click listener
        this.items = items;
        this.longClickListener = onLongClickListener;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //use layout inflator to inflate a view inside a viewholder and return it
        View todoView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new Viewholder(todoView);

        ////////////////parent is changed to viewGroup////////////////
    }

    //binds data to particular view holder
    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        //grab the item
        String item = items.get(i); ///////////// "position" varible is now index "i"//////////////

        //then bind to view holder
        viewholder.bind(item); ///Holder is now viewholder
    }

    //returns how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // container to provide easy access to viewholder
    class Viewholder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update view inside the viewholder with the data of the item string
        public void bind(String item) {
            tvItem.setText(item);
            //Checks for long holds
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Now we need to notify the listener which item was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition()); //Pass in the adapter (pressed items's) position/index
                    return true;
                }
            });
        }
    }
}
