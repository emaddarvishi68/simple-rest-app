package com.emad.simplerestapp.service.mapper.impl;

import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.dto.CommentDto;
import com.emad.simplerestapp.model.dto.PostDto;
import com.emad.simplerestapp.model.dto.TodoDto;
import com.emad.simplerestapp.service.mapper.api.Mapper;
import org.springframework.stereotype.Component;

/**
 * mapping entity to dto and dto to entity
 */
@Component
public class MapperImpl implements Mapper {
    @Override
    public PostDto map(Post post) {
        if (post == null) return null;
        return PostDto.builder()
                .body(post.getBody())
                .id(post.getId())
                .title(post.getTitle())
                .userId(post.getUserId())
                .build();
    }

    @Override
    public CommentDto map(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .body(comment.getBody())
                .id(comment.getId())
                .email(comment.getEmail())
                .name(comment.getName())
                .postId(comment.getPostId())
                .build();
    }

    @Override
    public TodoDto map(Todo todo) {
        if (todo == null) return null;
        return TodoDto.builder()
                .id(todo.getId())
                .completed(todo.isCompleted())
                .title(todo.getTitle())
                .userId(todo.getUserId())
                .build();
    }

    @Override
    public Post map(PostDto postDto) {
        if (postDto == null) return null;
        return Post.builder()
                .body(postDto.getBody())
                .id(postDto.getId())
                .title(postDto.getTitle())
                .userId(postDto.getUserId())
                .build();
    }

    @Override
    public Comment map(CommentDto commentDto) {
        if (commentDto == null) return null;
        return Comment.builder()
                .body(commentDto.getBody())
                .id(commentDto.getId())
                .email(commentDto.getEmail())
                .name(commentDto.getName())
                .postId(commentDto.getPostId())
                .build();
    }

    @Override
    public Todo map(TodoDto todoDto) {
        if (todoDto == null) return null;
        return Todo.builder()
                .id(todoDto.getId())
                .completed(todoDto.isCompleted())
                .title(todoDto.getTitle())
                .userId(todoDto.getUserId())
                .build();
    }
}
