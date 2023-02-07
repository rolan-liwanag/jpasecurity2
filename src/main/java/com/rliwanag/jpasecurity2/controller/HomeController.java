package com.rliwanag.jpasecurity2.controller;

import com.rliwanag.jpasecurity2.model.User;
import com.rliwanag.jpasecurity2.service.JpaUserDetailsService;
import com.rliwanag.jpasecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<User> userDtos = this.userService.getAllUsers();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity findUserByUsername(@PathVariable  String username) {
        Optional<User> userOpt = this.userService.findUserByUsername(username);
        if(userOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(userOpt.get(), HttpStatus.OK);
        }
    }

    @PostMapping(path="/create-user")
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            this.userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/greeting")
    public String everybodyAllowedPage() {
        return "everybody allowed page";
    }
}
