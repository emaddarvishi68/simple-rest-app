package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Post;

import java.util.List;

public interface PostService {
    Iterable<Post> save(List<Post> postList);
}
