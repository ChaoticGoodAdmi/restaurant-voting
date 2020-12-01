package com.topjava.kirill.restaurantvoting.controller;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T> {

    List<T> getAll();

    T get(Integer id);

    default ResponseEntity<T> createWithNewUri(T entity) {
        throw new NotYetImplementedException();
    }

    default void update(T entity, Integer id) {
        throw new NotYetImplementedException();
    }

    void delete(Integer id);
}
