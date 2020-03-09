package com.rf1.showposts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rf1.showposts.adapter.AdpaterPosts;
import com.rf1.showposts.model.Post;
import com.rf1.showposts.utils.JsonPostTask;
import com.rf1.showposts.utils.PostLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostLoader {

    private FloatingActionButton fab;
    private AdpaterPosts adapterPosts;
    private List<Post> listPosts;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Show Posts from JsonPlaceholder");

        listPosts = new ArrayList<>();


        adapterPosts = new AdpaterPosts(getApplicationContext(), listPosts);

        recyclerView = findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterPosts);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "It's Work! :)", Toast.LENGTH_SHORT).show();
            }
        });

        JsonPostTask jsonPostTask = new JsonPostTask(this);
        jsonPostTask.setPostLoader(this);
        jsonPostTask.execute("https://jsonplaceholder.typicode.com/posts");

    }

    @Override
    public void onResult(List<Post> posts) {

        adapterPosts.setPosts(posts);
        adapterPosts.notifyDataSetChanged();
    }
}
