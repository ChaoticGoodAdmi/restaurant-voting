package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.dto.RestaurantDto;
import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.repository.RestaurantRepository;
import com.topjava.kirill.restaurantvoting.util.DtoUtil;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Restaurant> getAllWithVotes() {
        log.info("Getting all restaurants with votes");
        return repository.findAllWithVotes();
    }

    public List<Restaurant> getAllWithMenuItemsAndVotes() {
        log.info("Getting all restaurants with menuItems and votes");
        return repository.findAllWithMenuItemsAndVotes();
    }

    public List<Restaurant> getWithVotesByDate(LocalDate date) {
        log.info("Getting all restaurants with votes");
        return repository.findWithVotesByDate(date);
    }

    public List<Restaurant> getAllWithMenuItemsAndVotesByDate(LocalDate date) {
        log.info("Getting all restaurants with menuItems and votes by date {}", date);
        return repository.findAllWithMenuItemsAndVotesByDate(date);
    }

    public List<RestaurantDto> getAllWithVoteCount(LocalDate date) {
        log.info("Getting all restaurants with vote counts");
        List<Restaurant> restaurants = repository.findWithVotesByDate(date);
        return restaurants.stream()
                .map(r -> DtoUtil.fromRestaurantOnDate(r, date))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant get(int id) {
        log.info("Getting a restaurant by ID {}", id);
        return checkNotFoundWithId(repository.findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant " + id + " not found")), id);
    }

    public Restaurant getWithMenuItems(Integer id) {
        log.info("Getting a restaurant with menuItems by ID {}", id);
        return checkNotFoundWithId(repository.findByIdWithMenuItems(id), id);
    }

    public Restaurant getByMenuDate(Integer id, LocalDate date) {
        log.info("Getting a restaurant with menuItems by ID {} on date {}", id, date);
        return checkNotFoundWithId(repository.findByMenuDate(id, date), id);
    }

    public Restaurant getWithVotesById(Integer id) {
        log.info("Getting restaurant with id {}", id);
        return checkNotFoundWithId(repository.findByIdWithVotes(id), id);
    }

    public Restaurant getWithVotesByIdAndDate(Integer id, LocalDate date) {
        log.info("Getting restaurant with votes by id {} and date {}", id, date);
        return checkNotFoundWithId(repository.findByIdAndVotesDate(id, date), id);
    }

    public Restaurant getWithMenuItemsAndVotesById(Integer id) {
        log.info("Getting restaurant with menuItems and votes by id {}", id);
        return checkNotFoundWithId(repository.findByIdWithMenuItemsAndVotes(id), id);
    }

    public Restaurant getWithMenuItemsAndVotesByIdAndDate(Integer id, LocalDate date) {
        log.info("Getting restaurant with menuItems and votes by id {}", id);
        return checkNotFoundWithId(repository.findByIdAndDateWithMenuItemsAndVotes(id, date), id);
    }

    public RestaurantDto getWithVoteCountById(Integer id, LocalDate date) {
        log.info("Getting restaurant vote count by id {}", id);
        Restaurant restaurant = checkNotFoundWithId(repository.findByIdAndVotesDate(id, date), id);
        System.out.println(restaurant.getVotes());
        return DtoUtil.fromRestaurantOnDate(restaurant, date);
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
