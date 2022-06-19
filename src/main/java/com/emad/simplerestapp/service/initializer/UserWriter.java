package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.UserService;
import com.emad.simplerestapp.service.impl.ResourceName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * read from users resource and write to db
 */
@Component
public class UserWriter implements CommandLineRunner {
    private final UserService userService;

    public UserWriter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.save(userService.fetchFromResource(ResourceName.USERS_RESOURCE));
    }
}
