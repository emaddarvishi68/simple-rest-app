package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exceptions.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.repository.CommentRepository;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.api.PostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Iterable<Comment> save(List<Comment> commentList) {
        return commentRepository.saveAll(commentList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(int id) {
        return commentRepository.getCommentsByPostId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllComments(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Comment> pagedResult = commentRepository.findAll(pageable);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Comment create(Comment comment) {
        Optional<Post> post = postService.getPostById(comment.getPostId());
        if(post.isPresent()){
            return commentRepository.save(comment);
        }
        throw new MasterEntityNotFoundException(String.format("There is no post with id %d",comment.getPostId()));//todo handle exception in controller advice
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByCommentId(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Comment update(Comment comment, Map<Object, Object> fields) {
        fields.forEach((k, v) -> {
            if (!k.toString().equalsIgnoreCase("id")) { // update any field except id
                Field field = ReflectionUtils.findField(Comment.class, (String) k);
                if (field != null) { //if field exists
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, comment, v);
                }
            }
        });
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public List<Comment> fetchFromResource(String resource) throws IOException {
        TypeReference<List<Comment>> typeReference = new TypeReference<List<Comment>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        return objectMapper.readValue(inputStream, typeReference);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }
}
