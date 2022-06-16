package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Comment;

import java.util.List;

public interface CommentService {

    Iterable<Comment> save(List<Comment> commentList);

}
