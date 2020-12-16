package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.repository.RestaurantRepository;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.*;

@Service
@Slf4j
public class RestaurantService implements BaseService<Restaurant> {

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> getAll() {
        log.info("Getting all restaurants");
        return repository.findAll();
    }

    public List<Restaurant> getAllWithMenuItems() {
        log.info("Getting all restaurants with menuItems");
        return repository.findAllWithMenuItems();
    }

    public List<Restaurant> getAllByMenuDate(LocalDate date) {
        log.info("Getting all restaurants by menu date {}", date);
        return repository.findAllByMenuItemsDate(date);
    }

    @Override
    public Restaurant get(int id) {
        log.info("Getting a restaurant by ID {}", id);
        return checkNotFoundWithId(repository.findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant " + id + " not found")), id);
    }

    public Restaurant getWithMenuItems(Integer id) {
        log.info("Getting a restaurant with menuItems by ID {}", id );
        return checkNotFoundWithId(repository.getByIdWithMenuItems(id), id);
    }

    public Restaurant getByMenuDate(Integer id, LocalDate date) {
        log.info("Getting a restaurant with menuItems by ID {} on date {}", id, date);
        return checkNotFoundWithId(repository.getByMenuDate(id, date), id);
    }

    @Transactional
    @Override
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @Transactional
    @Override
    public void update(Restaurant restaurant, Integer id) {
        Assert.notNull(restaurant, "Restaurant can't be null");
        assureEntityIdConsistent(restaurant, id);
        checkNotFoundWithId(get(id), id);
        repository.save(restaurant);
    }

    @Override
    public void delete(int id) {
            checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
