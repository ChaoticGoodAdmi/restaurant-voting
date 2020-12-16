package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.model.Dish;
import com.topjava.kirill.restaurantvoting.repository.DishRepository;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.*;

@Service
@Slf4j
public class DishService implements BaseService<Dish> {

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Dish> getAll() {
        log.info("Getting all dishes");
        return repository.findAll();
    }

    @Override
    public Dish get(int id) {
        log.info("Getting a dish by ID {}", id );
        return checkNotFoundWithId(repository.findById(id).orElseThrow(
                () -> new NotFoundException("Dish " + id + " not found")), id);
    }

    @Override
    public Dish create(Dish dish) {
        checkNew(dish);
        return repository.save(dish);
    }

    @Transactional
    @Override
    public void update(Dish dish, Integer id) {
        Assert.notNull(dish, "Dish can't be null");
        assureEntityIdConsistent(dish, id);
        checkNotFoundWithId(get(id), id);
        repository.save(dish);
    }

    @Transactional
    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
