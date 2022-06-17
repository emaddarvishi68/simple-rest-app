package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.UserService;
import com.emad.simplerestapp.staticvalues.ResourceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserWriter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public UserWriter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.save(userService.fetchFromResource(ResourceName.USERS_RESOURCE));
        logger.info("users was saved on db");
    }
}
