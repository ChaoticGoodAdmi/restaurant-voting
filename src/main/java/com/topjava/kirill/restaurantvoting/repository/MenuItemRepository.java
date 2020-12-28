package com.topjava.kirill.restaurantvoting.repository;

import com.topjava.kirill.restaurantvoting.model.MenuItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Transactional
    @Query("SELECT m FROM MenuItem m " +
            "LEFT JOIN FETCH m.dish d")
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<MenuItem> findAll();

    @Transactional
    @Query("SELECT m FROM MenuItem m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE m.date = ?1")
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    @OrderBy("id")
    List<MenuItem> findAllByDate(LocalDate date);

    @Transactional
    @Query("SELECT m FROM MenuItem m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE m.id = ?1")
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    Optional<MenuItem> findById(Integer id);

    @EntityGraph(attributePaths = {"dish"})
    List<MenuItem> findAllByRestaurantId(int restaurant_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem m WHERE m.id = ?1")
    int delete(int id);
}
