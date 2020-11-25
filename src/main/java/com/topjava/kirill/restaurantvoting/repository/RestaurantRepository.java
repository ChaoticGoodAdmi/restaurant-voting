package com.topjava.kirill.restaurantvoting.repository;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish")
    List<Restaurant> findAllWithMenuItems();

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish " +
            "WHERE m.date = ?1")
    List<Restaurant> findAllByMenuItemsDate(LocalDate date);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id = ?1 AND m.date = ?2")
    Restaurant getByMenuDate(int id, LocalDate date);

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = ?1")
    @Transactional
    int delete(int id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id = ?1")
    Restaurant getByIdWithMenuItems(int id);
}
