package com.topjava.kirill.restaurantvoting.controller.json;

public class View {

    public interface JsonRestaurantWithMenuItems {

    }

    public interface JsonMenuItemWithRestaurants {

    }

    public interface JsonRestaurantWithVotes {

    }

    public interface JsonRestaurantWithMenuItemsAndVotes extends JsonRestaurantWithMenuItems, JsonRestaurantWithVotes {

    }

    public interface JsonUserProfile {

    }

    public interface JsonUserWithVotes extends JsonUserProfile {

    }
}