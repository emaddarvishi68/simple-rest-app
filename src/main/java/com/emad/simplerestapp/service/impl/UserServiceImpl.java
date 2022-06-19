package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.UserRepository;
import com.emad.simplerestapp.service.api.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Iterable<User> save(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    @Override
    public List<User> fetchFromResource(String resource) throws IOException {
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        if (inputStream == null) {
            throw new FileNotFoundException("users.json was not found");
        }
        return objectMapper.readValue(inputStream, typeReference);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
