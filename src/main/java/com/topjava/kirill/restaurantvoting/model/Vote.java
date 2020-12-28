package com.topjava.kirill.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Vote extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "DATE")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    @NotNull
    @JsonIgnoreProperties("votes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate date, @NotNull User user, @NotNull Restaurant restaurant) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(LocalDate date, @NotNull User user, @NotNull Restaurant restaurant) {
        this(null, date, user, restaurant);
    }
}
