package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.UserRepository;
import com.emad.simplerestapp.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Iterable<User> save(List<User> userList) {
        return userRepository.saveAll(userList);
    }
}
