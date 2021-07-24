package com.gioidev.basedemo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.basedemo.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ImageView imageView;
    TextView textView;
    ArrayList<FbFriend> fbFriends;
    Context context;

    public MyAdapter(Context context, ImageView imageView, TextView textView, ArrayList<FbFriend> fbFriends) {
        this.context = context;
        this.imageView = imageView;
        this.textView = textView;
        this.fbFriends = fbFriends;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load("https://graph.facebook.com/ " + fbFriends.get(position).getId() + "/picture?type=large").into(holder.imageView);

        holder.textView.setText(fbFriends.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return fbFriends.size();
    }

    public void Clear(){
        fbFriends.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivUser);
            textView = itemView.findViewById(R.id.tvName);
        }
    }
}
