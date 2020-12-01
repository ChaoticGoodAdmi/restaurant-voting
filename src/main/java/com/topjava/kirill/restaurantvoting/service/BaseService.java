package com.topjava.kirill.restaurantvoting.service;

import java.util.List;

public interface BaseService<T> {

    List<T> getAll();

    T get(int id);

    T create(T entity);

    void update(T entity, Integer id);

    void delete(int id);
}
