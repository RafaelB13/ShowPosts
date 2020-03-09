package com.rf1.showposts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rf1.showposts.R;
import com.rf1.showposts.model.Post;

import java.util.List;

public class AdpaterPosts extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Post> posts;

    public AdpaterPosts(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.post_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts.clear();
        if (posts != null) {
            this.posts.addAll(posts);
        }
    }
}


class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewTitle;
    TextView textViewBody;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewBody = itemView.findViewById(R.id.text_view_body);
    }
}