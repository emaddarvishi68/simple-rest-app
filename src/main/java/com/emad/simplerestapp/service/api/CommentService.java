package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Comment;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentService {

    Iterable<Comment> save(List<Comment> commentList);

    List<Comment> getCommentsByPostId(int id);

    List<Comment> getAllComments(Integer pageNo, Integer pageSize);

    Comment create(Comment post);

    void deleteByCommentId(int id);

    Optional<Comment> getCommentById(int id);

    Comment update(Comment comment, Map<Object, Object> fields);

    List<Comment> fetchFromResource(String resource) throws IOException;

}
