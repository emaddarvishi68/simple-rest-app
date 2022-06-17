package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    Iterable<User> save(List<User> userList);

    List<User> fetchFromResource(String resource) throws IOException;

}
