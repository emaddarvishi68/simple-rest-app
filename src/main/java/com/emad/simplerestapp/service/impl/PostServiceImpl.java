package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.repository.PostRepository;
import com.emad.simplerestapp.service.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
