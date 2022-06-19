package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * save list of users
     * @param userList
     * @return Iterable<User>
     */
    Iterable<User> save(List<User> userList);

    /**
     * fetch users from resource
     * @param resource
     * @return List<User>
     * @throws IOException
     */
    List<User> fetchFromResource(String resource) throws IOException;

    /**
     * get user by id
     * @param id
     * @return Optional<User>
     */
    Optional<User> getUserById(int id);

}
