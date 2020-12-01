package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.model.Dish;
import com.topjava.kirill.restaurantvoting.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(DishController.REST_URL)
@Slf4j
public class DishController implements BaseController<Dish> {

    public static final String REST_URL = "/dish";

    private final DishService service;

    @Autowired
    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        List<Dish> dishes = service.getAll();
        log.info("Retrieved {} dishes", dishes.size());
        return dishes;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable Integer id) {
        Dish dish = service.get(id);
        log.info("Found dish {}", dish.toString());
        return dish;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithNewUri(@Valid @RequestBody Dish dish) {
        log.info("Creating new {}", dish);
        Dish created = service.create(dish);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Dish dish, @PathVariable Integer id) {
        log.info("Updating dish {} with data: {}", id, dish);
        service.update(dish, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Deleting dish {}", id);
        service.delete(id);
    }
}
