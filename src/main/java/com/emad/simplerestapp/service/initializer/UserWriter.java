package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.service.api.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class UserWriter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public UserWriter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/users.json");
        try {
            List<User> userList = mapper.readValue(inputStream,typeReference);
            userService.save(userList);
            logger.info("users was saved");
        } catch (IOException e){
            logger.error("Unable to save users: " + e.getMessage());
        }
    }
}
