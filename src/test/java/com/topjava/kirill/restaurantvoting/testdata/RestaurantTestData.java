package com.topjava.kirill.restaurantvoting.testdata;

import com.topjava.kirill.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.kirill.restaurantvoting.testdata.MenuItemTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int START_SEQ = 100000;

    public static final int RESTAURANT_ID_1 = START_SEQ;
    public static final int RESTAURANT_ID_2 = START_SEQ + 1;
    public static final int RESTAURANT_ID_3 = START_SEQ + 2;

    public static final Restaurant RESTAURANT_1 =
            new Restaurant(RESTAURANT_ID_1, "McDonalds", "Street st., 10",
                    List.of(MENU_ITEM_5, MENU_ITEM_6, MENU_ITEM_1, MENU_ITEM_2));

    public static final Restaurant RESTAURANT_2 =
            new Restaurant(RESTAURANT_ID_2, "Tokyo City", "Prospect pr., 1",
                    List.of(MENU_ITEM_9, MENU_ITEM_10, MENU_ITEM_7, MENU_ITEM_8, MENU_ITEM_3, MENU_ITEM_4));

    public static final Restaurant RESTAURANT_3 =
            new Restaurant(RESTAURANT_ID_3, "Столовая №1", "Невский пр., 1",
                    List.of(MENU_ITEM_11, MENU_ITEM_12));

    public static final List<Restaurant> RESTAURANTS =  List.of(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);

    public static final LocalDate TEST_DATE = LocalDate.of(2020, 11, 3);

    public static Restaurant getCreated() {
        return new Restaurant(null, "New", "New address", null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID_1, "Updated", "Updated address", null);
    }

    public static List<Restaurant> getAllRestaurantsForTestDate() {
        Restaurant r1 = new Restaurant(RESTAURANT_ID_2, RESTAURANT_2.getName(), RESTAURANT_2.getAddress(),
                List.of(MENU_ITEM_9, MENU_ITEM_10));
        Restaurant r2 = new Restaurant(RESTAURANT_ID_3, RESTAURANT_3.getName(), RESTAURANT_3.getAddress(),
                List.of(MENU_ITEM_11, MENU_ITEM_12));
        return List.of(r1, r2);
    }

    public static Restaurant getRestaurantForTestDate() {
        return new Restaurant(RESTAURANT_ID_2, RESTAURANT_2.getName(), RESTAURANT_2.getAddress(),
                List.of(MENU_ITEM_9, MENU_ITEM_10));
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("menuItems")
                .isEqualTo(expected);
    }

    public static void assertMatchWithMenuItems(Restaurant actual, Restaurant expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, List<Restaurant> expected) {
        assertThat(actual)
                .usingElementComparatorIgnoringFields("menuItems")
                .isEqualTo(expected);
    }

    public static void assertMatchWithMenuItems(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expected);
    }
}
