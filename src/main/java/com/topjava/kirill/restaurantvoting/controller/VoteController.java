package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.model.Vote;
import com.topjava.kirill.restaurantvoting.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
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
    @Secured("ROLE_ADMIN")
    public Vote get(@PathVariable Integer id) {
        Vote vote = service.get(id);
        log.info("Retrieved vote: {}", vote);
        return vote;
    }

    @GetMapping(value = "/user/{userId}/vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public Vote getVoteByUserAndDate(@PathVariable Integer userId,
                                       @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Vote vote = service.getByUserAndDate(userId, date);
        log.info("Got vote by user {} and date {}", userId, date);
        return vote;
    }

    @GetMapping(value = "/vote/count", params = {"restaurantId", "date"}, produces = APPLICATION_JSON_VALUE)
    public Long getVotesCountByRestaurantAndDate(@RequestParam Integer restaurantId,
                                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long count = service.countAllByRestaurantAndDate(restaurantId, date);
        log.info("Got votes count ({}) by restaurant {} and date {}", count, restaurantId, date);
        return count;
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
    @DeleteMapping(value = "/vote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete vote with id={}", id);
        service.delete(id);
    }
}
