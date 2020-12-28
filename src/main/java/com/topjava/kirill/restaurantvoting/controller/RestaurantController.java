package com.topjava.kirill.restaurantvoting.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.kirill.restaurantvoting.controller.json.View;
import com.topjava.kirill.restaurantvoting.dto.RestaurantDto;
import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(RestaurantController.REST_URL)
@Slf4j
@CrossOrigin("*")
public class RestaurantController implements BaseController<Restaurant> {

    public static final String REST_URL = "/restaurant";

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = service.getAll();
        log.info("Retrieved {} restaurants", restaurants.size());
        return restaurants;
    }

    @GetMapping(value = "/menu", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithMenuItems.class)
    public List<Restaurant> getAllWithMenuItems() {
        List<Restaurant> restaurants = service.getAllWithMenuItems();
        log.info("Retrieved {} restaurants with menuItems", restaurants.size());
        return restaurants;
    }

    @GetMapping(value = "/menu", params = "date", produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllByMenuDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Restaurant> restaurants = service.getAllByMenuDate(date);
        log.info("Found {} restaurants menus for date {}", restaurants.size(), date);
        return restaurants;
    }

    @GetMapping(value = "/vote", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public List<Restaurant> getAllWithVotes() {
        List<Restaurant> restaurants = service.getAllWithVotes();
        log.info("Retrieved all restaurants with votes");
        return restaurants;
    }

    @GetMapping(value = "/vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public List<Restaurant> getAllWithVotesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Restaurant> restaurants = service.getWithVotesByDate(date);
        log.info("Retrieved restaurants with votes by date {}", date);
        return restaurants;
    }

    @GetMapping(value = "/menu-vote", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithMenuItemsAndVotes.class)
    public List<Restaurant> getAllWithMenuItemsAndVotes() {
        List<Restaurant> restaurants = service.getAllWithMenuItemsAndVotes();
        log.info("Retrieved all restaurants with menuItems and votes");
        return restaurants;
    }

    @GetMapping(value = "/menu-vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithMenuItemsAndVotes.class)
    public List<Restaurant> getAllWithMenuItemsAndVotesByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Restaurant> restaurants = service.getAllWithMenuItemsAndVotesByDate(date);
        log.info("Retrieved all restaurants with menuItems and votes by date {}", date);
        return restaurants;
    }

    @GetMapping(value = "/vote-count", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public List<RestaurantDto> getAllWithVoteCount(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<RestaurantDto> restaurants = service.getAllWithVoteCount(date);
        log.info("Retrieved all restaurants with vote counts");
        return restaurants;
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable Integer id) {
        Restaurant restaurant = service.get(id);
        log.info("Found restaurant {}", restaurant.toString());
        return restaurant;
    }

    @GetMapping(value = "/{id}/menu", produces = APPLICATION_JSON_VALUE)
    public Restaurant getWithMenuItems(@PathVariable Integer id) {
        Restaurant restaurant = service.getWithMenuItems(id);
        log.info("Found restaurant {} with {} menuItems", restaurant, restaurant.getMenuItems().size());
        return restaurant;
    }

    @GetMapping(value = "/{id}/menu", params = "date", produces = APPLICATION_JSON_VALUE)
    public Restaurant getWithMenuItems(@PathVariable Integer id,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Restaurant restaurant = service.getByMenuDate(id, date);
        log.info("Found restaurant {} with {} menuItems for date {}", restaurant, restaurant.getMenuItems().size(), date);
        return restaurant;
    }

    @GetMapping(value = "/{id}/vote", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public Restaurant getByIdWithVotes(@PathVariable Integer id) {
        Restaurant restaurant = service.getWithVotesById(id);
        log.info("Found restaurant {} with votes", id);
        return restaurant;
    }

    @GetMapping(value = "/{id}/vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public Restaurant getWithVotesByIdAndDate(@PathVariable Integer id,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        Restaurant restaurant = service.getWithVotesByIdAndDate(id, date);
        log.info("Found restaurant {} with votes by date {}", id, date);
        return restaurant;
    }

    @GetMapping(value = "/{id}/menu-vote", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithMenuItemsAndVotes.class)
    public Restaurant getByIdWithMenuItemsAndVotes(@PathVariable Integer id) {
        Restaurant restaurant = service.getWithMenuItemsAndVotesById(id);
        log.info("Found restaurant {} with menus and votes", id);
        return restaurant;
    }

    @GetMapping(value = "/{id}/menu-vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithMenuItemsAndVotes.class)
    @Secured("ROLE_ADMIN")
    public Restaurant getWithMenuAndVotesByIdAndDate(@PathVariable Integer id,
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        Restaurant restaurant = service.getWithMenuItemsAndVotesByIdAndDate(id, date);
        log.info("Found restaurant {} with menuItems and votes by date {}", id, date);
        return restaurant;
    }

    @GetMapping(value = "/{id}/vote-count", params = "date", produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonRestaurantWithVotes.class)
    public RestaurantDto getWithVoteCountById(@PathVariable Integer id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RestaurantDto restaurant = service.getWithVoteCountById(id, date);
        log.info("Retrieved restaurant {} with vote count", id);
        return restaurant;
    }

    @Override
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Restaurant> createWithNewUri(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);
        log.info("Created new {}", restaurant);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id) {
        service.update(restaurant, id);
        log.info("Updated restaurant {} with data: {}", id, restaurant);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
        log.info("Deleted restaurant {}", id);
    }
}
