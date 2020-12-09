package com.topjava.kirill.restaurantvoting.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItemDto extends BaseDto {

    private Integer restaurantId;
    private LocalDate date;
    private Integer dishId;
}
