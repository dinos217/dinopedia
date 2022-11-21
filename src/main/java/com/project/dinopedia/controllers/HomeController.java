package com.project.dinopedia.controllers;

import com.project.dinopedia.dtos.LoginRequestDto;
import com.project.dinopedia.dtos.UserDto;
import com.project.dinopedia.services.AuthService;
import com.project.dinopedia.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    private UserService userService;
    private AuthService authService;

    @Autowired
    public HomeController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        log.info("Started saving new user " + userDto.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userDto));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

        log.info("Started authentication of user: " + loginRequestDto.getUsername());

        try {
            authService.authenticate(loginRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(loginRequestDto.getUsername() + " logged in successfully.");
        } catch (BadCredentialsException e) {
            log.info("ERROR: Bad login attempt.");
            throw new BadCredentialsException("Invalid credentials.");
        }
    }
}
