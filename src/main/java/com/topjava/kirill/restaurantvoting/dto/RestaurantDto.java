package com.topjava.kirill.restaurantvoting.dto;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantDto extends BaseDto {

    private Restaurant restaurant;
    private LocalDate localDate;
    private int voteCount;
}
