package com.topjava.kirill.restaurantvoting.repository;

import com.topjava.kirill.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id = ?1")
    @Transactional
    int delete(int id);
}
