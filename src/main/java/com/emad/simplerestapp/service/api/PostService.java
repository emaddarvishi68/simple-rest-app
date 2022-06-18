package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Post;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {
    Iterable<Post> save(List<Post> postList);

    List<Post> getAllPosts(Integer pageNo, Integer pageSize);

    Optional<Post> getPostById(int id);

    List<Post> getAllPostsByTitle(String title);

    Post create(Post post);

    Optional<Post> deleteByPostId(int id);

    Optional<Post> update(int id, Map<Object, Object> fields);

    List<Post> fetchFromResource(String resource) throws IOException;

}
