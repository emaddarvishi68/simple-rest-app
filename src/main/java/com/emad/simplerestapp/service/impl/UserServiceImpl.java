package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.UserRepository;
import com.emad.simplerestapp.service.api.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Iterable<User> save(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    @Override
    public List<User> fetchFromResource(String resource) throws IOException {
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        return objectMapper.readValue(inputStream,typeReference);
    }
}
