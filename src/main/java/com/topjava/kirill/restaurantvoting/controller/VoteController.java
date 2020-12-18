package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.model.Vote;
import com.topjava.kirill.restaurantvoting.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class VoteController implements BaseController<Vote> {

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/vote", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public List<Vote> getAll() {
        List<Vote> votes = service.getAll();
        log.info("Retrieved {} votes", votes.size());
        return votes;
    }

    @Override
    @GetMapping(value = "/vote/{id}")
    public Vote get(@PathVariable Integer id) {
        Vote vote = service.get(id);
        log.info("Retrieved vote: {}", vote);
        return vote;
    }

    @PutMapping(value = "/profile/vote", params = "restaurantId", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> castVote(@Param("restaurantId") Integer restaurantId,
                                         @AuthenticationPrincipal AuthorizedUser authUser) {
        Integer userId = authUser.getId();
        log.info("Saving new vote of user {} for restaurant {}", userId, restaurantId);
        Vote created = service.create(LocalDateTime.now(), userId, restaurantId);
        URI newResourceUri = UriBuilder.buildFromEntity(created, "/vote/");
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @Override
    public void update(Vote entity, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
