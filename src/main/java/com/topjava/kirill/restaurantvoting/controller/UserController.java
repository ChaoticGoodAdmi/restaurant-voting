package com.topjava.kirill.restaurantvoting.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.kirill.restaurantvoting.controller.json.View;
import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserController.REST_URL)
@Secured("ROLE_ADMIN")
@Slf4j
public class UserController implements BaseController<User> {

    public static final String REST_URL = "/user";

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUserProfile.class)
    public List<User> getAll() {
        List<User> users = service.getAll();
        log.info("Got {} users", users.size());
        return users;
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUserProfile.class)
    public User get(@PathVariable Integer id) {
        User user = service.get(id);
        log.info("Got user: {}", user);
        return user;
    }

    @GetMapping(value = "/by", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUserProfile.class)
    public User getByEmail(@RequestParam String email) {
        User user = service.getByEmail(email);
        log.info("Got user: {}", user);
        return user;
    }

    @GetMapping(value = "/{id}/vote", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUserWithVotes.class)
    public User getByIdWithVotes(@PathVariable Integer id) {
        User user = service.getWithVotes(id);
        log.info("Got user {} with votes", id);
        return user;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUserProfile.class)
    public ResponseEntity<User> createWithNewUri(@Valid @RequestBody User user) {
        User created = service.create(user);
        log.info("Created new {}", user);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
        service.update(userDto, id);
        log.info("Updated userDto {} with data {}", userDto, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
        log.info("Deleted user {}", id);
    }
}
