package com.topjava.kirill.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "menu_item",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"restaurant_id", "date"},
                name = "menu_item_restaurant_id_date_index"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuItem extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @JsonIgnoreProperties("menuDishes")
    private Restaurant restaurant;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;
}
