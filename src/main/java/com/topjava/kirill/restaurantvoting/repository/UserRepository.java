package com.topjava.kirill.restaurantvoting.repository;

import com.topjava.kirill.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

    Optional<User> getByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(Integer integer);

    @Transactional
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.votes v " +
            "LEFT JOIN FETCH v.restaurant " +
            "WHERE u.id = ?1")
    User findByIdWithVotes(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = ?1")
    int delete(int id);
}
