package com.rf1.showposts.utils;

import com.rf1.showposts.model.Post;

import java.util.List;

public interface PostLoader {

    void onResult(List<Post> posts);
}
