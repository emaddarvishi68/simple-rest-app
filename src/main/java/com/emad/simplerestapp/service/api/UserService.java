package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.User;

import java.util.List;

public interface UserService {
    Iterable<User> save(List<User> userList);
}
