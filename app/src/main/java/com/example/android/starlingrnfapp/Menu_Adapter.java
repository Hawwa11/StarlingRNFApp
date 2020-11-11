package com.example.android.starlingrnfapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Menu_Adapter extends RecyclerView.Adapter<Menu_Adapter.MENUVH>{
    ArrayList<Menu> recycler;


    public Menu_Adapter(ArrayList<Menu> recycler) {
        this.recycler = recycler;
    }

    @NonNull
    @Override
    public MENUVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu,parent,false);
        MENUVH recyclerViewHolder = new MENUVH(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MENUVH holder, int position) {
        Menu RecyclerView=recycler.get(position);
        holder.image.setImageResource(RecyclerView.getImage());
        holder.title.setText(RecyclerView.getTitle());
//        holder.description.setText(RecyclerView.getDescription());
    }

    @Override
    public int getItemCount() {
       return recycler.size();
    }

    public class MENUVH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,description;
        public MENUVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_menu);
            title = itemView.findViewById(R.id.item_name);
//            description = itemView.findViewById(R.id.menu_desc);
        }
    }
}
