package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.topjava.kirill.restaurantvoting.testdata.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Slf4j
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        assertMatch(restaurants, RESTAURANTS);
    }

    @Test
    void getAllWithMenuItems() {
        List<Restaurant> restaurants = service.getAllWithMenuItems();
        assertMatchWithMenuItems(restaurants, RESTAURANTS);
    }

    @Test
    void getAllByMenuDate() {
        List<Restaurant> restaurants = service.getAllByMenuDate(TEST_DATE);
        assertMatchWithMenuItems(restaurants, getAllRestaurantsForTestDate());
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID_1);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void getWithMenuItems() {
        Restaurant restaurant = service.get(RESTAURANT_ID_3);
        assertMatchWithMenuItems(restaurant, RESTAURANT_3);
    }

    @Test
    void getByMenuDate() {
        Restaurant restaurant = service.getByMenuDate(RESTAURANT_ID_2, TEST_DATE);
        assertMatch(restaurant, getRestaurantForTestDate());
    }

    @Test
    void create() {
        Restaurant restaurant = getCreated();
        Restaurant newRestaurant = service.create(restaurant);
        assertMatch(restaurant, service.get(newRestaurant.getId()));
    }

    @Test
    void update() {
        Restaurant restaurant = getUpdated();
        service.update(restaurant, restaurant.getId());
        assertMatch(restaurant, service.get(restaurant.getId()));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_ID_3);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_3));
    }
}