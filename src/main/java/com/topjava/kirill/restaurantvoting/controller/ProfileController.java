package com.topjava.kirill.restaurantvoting.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.controller.json.View;
import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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
    @JsonView(View.JsonUserProfile.class)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        int id = authUser.getId();
        User user = service.get(id);
        log.info("got user with id={}", id);
        return user;
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        Integer id = authUser.getId();
        service.delete(id);
        log.info("deleted user {}", id);
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(View.JsonUserProfile.class)
    public ResponseEntity<User> register(@RequestBody @Valid UserDto userDto) {
        log.info("Creating new {}", userDto);
        User created = service.create(userDto);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserDto userDto,
                       @AuthenticationPrincipal AuthorizedUser authUser) {
        int id = authUser.getId();
        service.updateProfile(userDto, id);
        log.info("Updated user with id={}", id);
    }

    @PostMapping(value = "/change-password", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestParam("password") String oldPassword,
                               @RequestParam("newPassword") String newPassword,
                               @AuthenticationPrincipal AuthorizedUser authUser) {
        service.changePassword(authUser.getId(), oldPassword, newPassword);
        log.info("Changed user password with id={}", authUser.getId());
    }
}
