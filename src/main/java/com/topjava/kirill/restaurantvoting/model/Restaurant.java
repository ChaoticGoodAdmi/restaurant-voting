package com.topjava.kirill.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "restaurant")
@Data
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "address")
    @NotBlank
    @Size(min = 2, max = 255)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnoreProperties(value = "restaurant")
    @ToString.Exclude
    private List<MenuItem> menuItems;

    public Restaurant(Integer id, String name, String address, List<MenuItem> menuItems) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menuItems = menuItems;
    }
}


