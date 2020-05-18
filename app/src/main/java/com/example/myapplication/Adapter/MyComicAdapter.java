package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.myapplication.ChapterActivity;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Interface.IRecyclerOnClick;
import com.example.myapplication.Model.Comic;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> {
    Context context;
    List<Comic> comicList;

    public MyComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.comic_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(comicList.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(comicList.get(position).getName());
        //
        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                //start new activity
                context.startActivity(new Intent(context, ChapterActivity.class));
                Common.selected_comic = comicList.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.image_view);
            textView = (TextView)itemView.findViewById(R.id.manga_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            iRecyclerOnClick.onClick(view, getAdapterPosition());
        }
    }
}
