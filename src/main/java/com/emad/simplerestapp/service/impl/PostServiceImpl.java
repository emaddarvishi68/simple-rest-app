package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.repository.PostRepository;
import com.emad.simplerestapp.service.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Iterable<Post> save(List<Post> postList) {
        return postRepository.saveAll(postList);
    }
}
