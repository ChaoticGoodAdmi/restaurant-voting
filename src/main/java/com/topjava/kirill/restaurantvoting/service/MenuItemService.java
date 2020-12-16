package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.dto.MenuItemDto;
import com.topjava.kirill.restaurantvoting.model.Dish;
import com.topjava.kirill.restaurantvoting.model.MenuItem;
import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.repository.MenuItemRepository;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.*;

@Service
@Slf4j
public class MenuItemService implements BaseService<MenuItem> {

    private final MenuItemRepository repository;
    private final RestaurantService restaurantService;
    private final DishService dishService;

    @Autowired
    public MenuItemService(MenuItemRepository repository, RestaurantService restaurantService, DishService dishService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    @Override
    public List<MenuItem> getAll() {
        log.info("Getting all menu items");
        return repository.findAll();
    }

    public List<MenuItem> getAllByDate(LocalDate date) {
        log.info("Getting menu items by date {}", date);
        return repository.findAllByDate(date);
    }

    @Override
    public MenuItem get(int id) {
        log.info("Getting a menu item by ID {}", id);
        return checkNotFoundWithId(repository.findById(id).orElseThrow(
                () -> new NotFoundException("Menu item " + id + " not found")), id);
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        checkNew(menuItem);
        return repository.save(menuItem);
    }

    public MenuItem create(MenuItemDto menuItemDto) {
        checkNew(menuItemDto);
        MenuItem menuItem = convertDtoToMenuItem(menuItemDto);
        return repository.save(menuItem);
    }

    @Transactional
    @Override
    public void update(MenuItem menuItem, Integer id) {
        Assert.notNull(menuItem, "Menu item can't be null");
        assureEntityIdConsistent(menuItem, id);
        checkNotFoundWithId(get(id), id);
        repository.save(menuItem);
    }

    @Transactional
    public void update(MenuItemDto menuItemDto, Integer id) {
        MenuItem menuItem = convertDtoToMenuItem(menuItemDto);
        update(menuItem, id);
    }

    @Transactional
    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    private MenuItem convertDtoToMenuItem(MenuItemDto menuItemDto) {
        Dish dish = dishService.get(menuItemDto.getDishId());
        Restaurant restaurant = restaurantService.get(menuItemDto.getRestaurantId());
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);
        menuItem.setDate(menuItemDto.getDate());
        menuItem.setDish(dish);
        return menuItem;
    }
}
