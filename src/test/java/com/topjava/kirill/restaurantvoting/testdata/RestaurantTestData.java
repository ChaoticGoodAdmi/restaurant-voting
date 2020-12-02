package com.topjava.kirill.restaurantvoting.testdata;

import com.topjava.kirill.restaurantvoting.model.Restaurant;

import java.util.List;

import static com.topjava.kirill.restaurantvoting.testdata.MenuItemTestData.*;

public class RestaurantTestData {
    public static final int START_SEQ = 100000;

    public static final int RESTAURANT_ID_1 = START_SEQ;
    public static final int RESTAURANT_ID_2 = START_SEQ + 1;


    public static final Restaurant RESTAURANT_1 =
            new Restaurant(RESTAURANT_ID_1, "McDonalds", "Street st., 10",
                    List.of(MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_5, MENU_ITEM_6, MENU_ITEM_9, MENU_ITEM_10));

    public static final Restaurant RESTAURANT_2 =
            new Restaurant(RESTAURANT_ID_2, "Токио Сити", "Prospect pr., 1",
                    List.of(MENU_ITEM_3, MENU_ITEM_4, MENU_ITEM_7, MENU_ITEM_8));



    public static final List<Restaurant> RESTAURANTS =  List.of(RESTAURANT_1, RESTAURANT_2);
}
