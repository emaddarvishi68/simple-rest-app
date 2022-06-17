package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public PostServiceImpl(PostRepository postRepository,ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Iterable<Post> save(List<Post> postList) {
        return postRepository.saveAll(postList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> pagedResult = postRepository.findAll(pageable);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> getPostById(int id) {
        return postRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPostsByTitle(String title) {
        return postRepository.getAllPostsByTitle(title);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByPostId(int id) {
        postRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Post update(Post post, Map<Object, Object> fields) {
        fields.forEach((k, v) -> {
            if (!k.toString().equalsIgnoreCase("id")) { // update any field except id
                Field field = ReflectionUtils.findField(Post.class, (String) k);
                if (field != null) { //if field exists
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, post, v);
                }
            }
        });
        return postRepository.saveAndFlush(post);
    }

    @Override
    public List<Post> fetchFromResource(String resource) throws IOException {
        TypeReference<List<Post>> typeReference = new TypeReference<List<Post>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        return objectMapper.readValue(inputStream,typeReference);
    }
}
