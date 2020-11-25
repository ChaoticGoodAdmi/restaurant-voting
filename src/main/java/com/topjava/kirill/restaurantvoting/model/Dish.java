package com.topjava.kirill.restaurantvoting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "dish")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dish extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
