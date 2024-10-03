package com.example.demo.infrastructure.inbound.controlllers;


import com.example.demo.application.UserService;
import com.example.demo.domain.exceptions.UserException;
import com.example.demo.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser) throws UserException {
        try {
            return new ResponseEntity<>(this.userService.createUser(newUser), HttpStatus.CREATED);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}