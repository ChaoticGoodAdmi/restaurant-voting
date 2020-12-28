package com.topjava.kirill.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "dish")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dish extends AbstractNamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price", nullable = false)
    @NotNull
    private BigDecimal price;

    public Dish(Integer id, String name, @NotNull BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        if (!super.equals(o)) return false;

        Dish dish = (Dish) o;

        if (!id.equals(dish.id)) return false;
        if (!name.equals(dish.name)) return false;
        return price.equals(dish.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
