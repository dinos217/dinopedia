package com.project.dinopedia.controllers;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}/favourites")
    ResponseEntity<List<DinosaurDto>> getUserFavourites(@PathVariable String username) {
        log.info("Started getting user's " + username + " favourite dinosaurs...");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserFavourites(username));
    }
}
