package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(ProfileController.REST_URL)
@Slf4j
public class ProfileController {

    public static final String REST_URL = "/profile";

    private final UserService service;

    @Autowired
    public ProfileController(UserService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        int id = authUser.getId();
        log.info("get user with id={}", id);
        return service.get(id);
    }
}
