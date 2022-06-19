package com.emad.simplerestapp.service.mapper.api;

import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.dto.CommentDto;
import com.emad.simplerestapp.model.dto.PostDto;
import com.emad.simplerestapp.model.dto.TodoDto;

public interface Mapper {

    PostDto map(Post post);

    CommentDto map(Comment comment);

    TodoDto map(Todo todo);

    Post map(PostDto postDto);

    Comment map(CommentDto commentDto);

    Todo map(TodoDto todoDto);

}
