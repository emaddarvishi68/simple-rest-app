package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.repository.CommentRepository;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.api.PostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ObjectMapper objectMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ObjectMapper objectMapper, PostService postService) {
        this.commentRepository = commentRepository;
        this.objectMapper = objectMapper;
        this.postService = postService;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Iterable<Comment> save(List<Comment> commentList) {
        return commentRepository.saveAll(commentList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(int postId) throws MasterEntityNotFoundException {
        Post post = postService.getPostById(postId).orElseThrow(() -> new MasterEntityNotFoundException("There is no Post with id: " + postId));
        return commentRepository.getCommentsByPostId(post.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> getAllComments(Integer pageNo, Integer pageSize) {
        return commentRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Optional<Comment> create(Comment comment) throws MasterEntityNotFoundException {
        postService.getPostById(comment.getPostId()).orElseThrow(() -> new MasterEntityNotFoundException("There is no post with id: " + comment.getPostId()));
        return Optional.of(commentRepository.save(comment));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Optional<Comment> deleteByCommentId(int id) {
        Optional<Comment> comment = getCommentById(id);
        comment.ifPresent(commentRepository::delete);
        return comment;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Optional<Comment> update(int id, Map<Object, Object> fields) {
        Optional<Comment> comment = getCommentById(id);
        comment.ifPresent((Comment e) ->
                fields.forEach((k, v) -> {
                    if (!k.toString().equalsIgnoreCase("id")) { // update any field except id
                        Field field = ReflectionUtils.findField(Comment.class, (String) k);
                        if (field != null) { //if field exists
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, e, v);
                        }
                    }
                }));
        return comment.map(commentRepository::save);
    }

    @Override
    public List<Comment> fetchFromResource(String resource) throws IOException {
        TypeReference<List<Comment>> typeReference = new TypeReference<List<Comment>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        if (inputStream == null) {
            throw new FileNotFoundException("comments.json was not found");
        }
        return objectMapper.readValue(inputStream, typeReference);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

}