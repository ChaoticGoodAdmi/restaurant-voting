package com.topjava.kirill.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuItem extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @JsonIgnoreProperties("menuDishes")
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @ToString.Exclude
    private Dish dish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MenuItem menuItem = (MenuItem) o;

        if (!id.equals(menuItem.id)) return false;
        if (!date.equals(menuItem.date)) return false;
        return dish.equals(menuItem.dish);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + dish.hashCode();
        return result;
    }
}
