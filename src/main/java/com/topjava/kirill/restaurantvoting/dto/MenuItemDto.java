package com.topjava.kirill.restaurantvoting.dto;

import com.topjava.kirill.restaurantvoting.model.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItemDto extends AbstractBaseEntity {

    private Integer id;
    private Integer restaurantId;
    private LocalDate date;
    private Integer dishId;
}
