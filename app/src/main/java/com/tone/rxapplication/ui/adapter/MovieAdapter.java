package com.tone.rxapplication.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tone.rxapplication.R;
import com.tone.rxapplication.entity.Subject;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private List<Subject> list = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Subject> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        Subject subject = list.get(viewHolder.getAdapterPosition());
        Holder holder= (Holder) viewHolder;
        holder.title.setText(""+subject.getTitle());
        Glide.with(context).load(subject.getImages().getLarge()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
