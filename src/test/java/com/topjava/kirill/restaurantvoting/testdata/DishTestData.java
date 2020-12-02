package com.topjava.kirill.restaurantvoting.testdata;

import com.topjava.kirill.restaurantvoting.model.Dish;

import java.math.BigDecimal;

public class DishTestData {

    public static final int START_SEQ = 100000;
    public static final int DISH_ID_1 = START_SEQ;
    public static final int DISH_ID_2 = START_SEQ + 1;
    public static final int DISH_ID_3 = START_SEQ + 2;
    public static final int DISH_ID_4 = START_SEQ + 3;
    public static final int DISH_ID_5 = START_SEQ + 4;
    public static final int DISH_ID_6 = START_SEQ + 5;
    public static final int DISH_ID_7 = START_SEQ + 6;
    public static final int DISH_ID_8 = START_SEQ + 7;

    public static final Dish DISH_1 = new Dish(DISH_ID_1,"Burger", BigDecimal.valueOf(200));
    public static final Dish DISH_2 = new Dish(DISH_ID_2,"Burger", BigDecimal.valueOf(150));
    public static final Dish DISH_3 = new Dish(DISH_ID_3,"Burger", BigDecimal.valueOf(1000));
    public static final Dish DISH_4 = new Dish(DISH_ID_4,"Burger", BigDecimal.valueOf(1500));
    public static final Dish DISH_5 = new Dish(DISH_ID_5,"Burger", BigDecimal.valueOf(300));
    public static final Dish DISH_6 = new Dish(DISH_ID_6,"Burger", BigDecimal.valueOf(500));
    public static final Dish DISH_7 = new Dish(DISH_ID_7,"Burger", BigDecimal.valueOf(1500));
    public static final Dish DISH_8 = new Dish(DISH_ID_8,"Burger", BigDecimal.valueOf(1500));

}