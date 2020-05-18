package com.example.myapplication.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.example.myapplication.Common.Common;
import com.example.myapplication.Interface.IRecyclerOnClick;
import com.example.myapplication.Model.Chapter;
import com.example.myapplication.R;
import com.example.myapplication.Service.ViewDetail;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {

    Context context;
    List<Chapter> chapterList;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_chapter_number.setText(new StringBuilder(chapterList.get(position).Name));


        Common.selected_chapter = chapterList.get(position);
        Common.chapter_index = position;

        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_chapter = chapterList.get(position);
                Common.chapter_index = position;
                // move to new Activity - ViewDetail
                context.startActivity(new Intent(context,ViewDetail.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView txt_chapter_number;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chapter_number = (TextView) itemView.findViewById(R.id.txt_chapter_number);
            itemView.setOnClickListener((View.OnClickListener) this);
        }
         public void onClick(View view){
            iRecyclerOnClick.onClick(view, getAdapterPosition());
         }
    }
}
