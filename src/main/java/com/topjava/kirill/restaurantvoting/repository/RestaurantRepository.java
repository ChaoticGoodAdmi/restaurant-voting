package com.topjava.kirill.restaurantvoting.repository;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
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
    Restaurant findByMenuDate(int id, LocalDate date);

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = ?1")
    @Transactional
    int delete(int id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id = ?1")
    Restaurant findByIdWithMenuItems(int id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE m.date = ?1")
    List<Restaurant> findAllByDate(LocalDate date);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    List<Restaurant> findAllWithVotes();

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    List<Restaurant> findAllWithMenuItemsAndVotes();

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "INNER JOIN FETCH r.votes v " +
            "WHERE v.date = ?1")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    List<Restaurant> findWithVotesByDate(LocalDate date);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE m.date = ?1 AND v.date = ?1")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    List<Restaurant> findAllWithMenuItemsAndVotesByDate(LocalDate date);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "WHERE r.id = ?1")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    Restaurant findByIdWithVotes(Integer id);

    @Transactional
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    Restaurant findByIdAndVotesDate(Integer id, LocalDate date);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id = ?1")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    Restaurant findByIdWithMenuItemsAndVotes(Integer id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH r.menuItems m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id = ?1 AND m.date = ?2 AND v.date = ?2")
    @EntityGraph(attributePaths = {"votes", "votes.user"})
    Restaurant findByIdAndDateWithMenuItemsAndVotes(Integer id, LocalDate date);
}
