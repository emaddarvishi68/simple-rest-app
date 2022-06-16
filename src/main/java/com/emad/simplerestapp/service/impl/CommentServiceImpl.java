package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.repository.CommentRepository;
import com.emad.simplerestapp.service.api.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Iterable<Comment> save(List<Comment> commentList) {
        return commentRepository.saveAll(commentList);
    }
}
