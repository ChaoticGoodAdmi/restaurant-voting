package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.kirill.restaurantvoting.testdata.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Sql(scripts = "classpath:db/data.sql", config = @SqlConfig(encoding = "UTF-8"))
@Transactional
@Slf4j
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        assertThat(restaurants)
                .usingElementComparatorIgnoringFields("menuItems")
                .isEqualTo(RESTAURANTS);
    }

    @Test
    void getAllWithMenuItems() {
        List<Restaurant> restaurants = service.getAllWithMenuItems();
        assertThat(restaurants)
                .usingFieldByFieldElementComparator()
                .isEqualTo(RESTAURANTS);
    }

    @Test
    void getAllByMenuDate() {
        List<Restaurant> restaurants = service.getAllByMenuDate(LocalDate.of(2020, 11, 3));
        assertThat(restaurants)
                .containsAll(List.of(RESTAURANT_1));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID_1);
        assertThat(restaurant)
                .usingRecursiveComparison()
                .ignoringFields("menuItems")
                .isEqualTo(RESTAURANT_1);
    }

    //@Test
    void getWithMenuItems() {

    }

    //@Test
    void getByMenuDate() {
    }

    //@Test
    void create() {
    }

    //@Test
    void update() {
    }

    //@Test
    void delete() {
    }
}