package com.topjava.kirill.restaurantvoting.controller;

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
public class RestaurantController implements BaseController<Restaurant>{

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

    @Override
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Restaurant> createWithNewUri(@Valid @RequestBody Restaurant restaurant) {
        log.info("Creating new {}", restaurant);
        Restaurant created = service.create(restaurant);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id) {
        log.info("Updating restaurant {} with data: {}", id, restaurant);
        service.update(restaurant, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable Integer id) {
        log.info("Deleting restaurant {}", id);
        service.delete(id);
    }
}
