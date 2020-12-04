package com.topjava.kirill.restaurantvoting.testdata;

import com.topjava.kirill.restaurantvoting.model.MenuItem;

import java.time.LocalDate;

import static com.topjava.kirill.restaurantvoting.testdata.DishTestData.*;
import static com.topjava.kirill.restaurantvoting.testdata.RestaurantTestData.*;

public class MenuItemTestData {

    public static final int START_SEQ = 100000;
    public static final int MENU_ITEM_ID_1 = START_SEQ;
    public static final int MENU_ITEM_ID_2 = START_SEQ + 1;
    public static final int MENU_ITEM_ID_3 = START_SEQ + 2;
    public static final int MENU_ITEM_ID_4 = START_SEQ + 3;
    public static final int MENU_ITEM_ID_5 = START_SEQ + 4;
    public static final int MENU_ITEM_ID_6 = START_SEQ + 5;
    public static final int MENU_ITEM_ID_7 = START_SEQ + 6;
    public static final int MENU_ITEM_ID_8 = START_SEQ + 7;
    public static final int MENU_ITEM_ID_9 = START_SEQ + 8;
    public static final int MENU_ITEM_ID_10 = START_SEQ + 9;
    public static final int MENU_ITEM_ID_11 = START_SEQ + 10;
    public static final int MENU_ITEM_ID_12 = START_SEQ + 11;

    public static final MenuItem MENU_ITEM_1 =
            new MenuItem(MENU_ITEM_ID_1, RESTAURANT_1, LocalDate.of(2020, 11, 1), DISH_1);
    public static final MenuItem MENU_ITEM_2 =
            new MenuItem(MENU_ITEM_ID_2, RESTAURANT_1, LocalDate.of(2020, 11, 1), DISH_2);
    public static final MenuItem MENU_ITEM_3 =
            new MenuItem(MENU_ITEM_ID_3, RESTAURANT_2, LocalDate.of(2020, 11, 1), DISH_3);
    public static final MenuItem MENU_ITEM_4 =
            new MenuItem(MENU_ITEM_ID_4, RESTAURANT_2, LocalDate.of(2020, 11, 1), DISH_4);
    public static final MenuItem MENU_ITEM_5 =
            new MenuItem(MENU_ITEM_ID_5, RESTAURANT_1, LocalDate.of(2020, 11, 2), DISH_5);
    public static final MenuItem MENU_ITEM_6 =
            new MenuItem(MENU_ITEM_ID_6, RESTAURANT_1, LocalDate.of(2020, 11, 2), DISH_6);
    public static final MenuItem MENU_ITEM_7 =
            new MenuItem(MENU_ITEM_ID_7, RESTAURANT_2, LocalDate.of(2020, 11, 2), DISH_7);
    public static final MenuItem MENU_ITEM_8 =
            new MenuItem(MENU_ITEM_ID_8, RESTAURANT_2, LocalDate.of(2020, 11, 2), DISH_8);
    public static final MenuItem MENU_ITEM_9 =
            new MenuItem(MENU_ITEM_ID_9, RESTAURANT_2, LocalDate.of(2020, 11, 3), DISH_1);
    public static final MenuItem MENU_ITEM_10 =
            new MenuItem(MENU_ITEM_ID_10, RESTAURANT_2, LocalDate.of(2020, 11, 3), DISH_2);
    public static final MenuItem MENU_ITEM_11 =
            new MenuItem(MENU_ITEM_ID_11, RESTAURANT_3, LocalDate.of(2020, 11, 3), DISH_3);
    public static final MenuItem MENU_ITEM_12 =
            new MenuItem(MENU_ITEM_ID_12, RESTAURANT_3, LocalDate.of(2020, 11, 3), DISH_4);
}