package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.dto.MenuItemDto;
import com.topjava.kirill.restaurantvoting.model.MenuItem;
import com.topjava.kirill.restaurantvoting.service.MenuItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(MenuItemController.REST_URL)
@Slf4j
public class MenuItemController implements BaseController<MenuItem> {

    public static final String REST_URL = "/menu";

    private final MenuItemService service;

    @Autowired
    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public List<MenuItem> getAll() {
        List<MenuItem> menuItems = service.getAll();
        log.info("Retrieved {} menu items", menuItems.size());
        return menuItems;
    }

    @GetMapping(value = "", params = "date", produces = APPLICATION_JSON_VALUE)
    public List<MenuItem> getAllByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MenuItem> menuItems = service.getAllByDate(date);
        log.info("Found {} menu items menus for date {}", menuItems.size(), date);
        return menuItems;
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public MenuItem get(@PathVariable Integer id) {
        MenuItem menuItem = service.get(id);
        log.info("Found menu item {}", menuItem.toString());
        return menuItem;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithNewUri(@Valid @RequestBody MenuItemDto menuItemDto) {
        log.info("Creating new {}", menuItemDto);
        MenuItem created = service.create(menuItemDto);
        URI newResourceUri = UriBuilder.buildFromEntity(created, REST_URL);
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody MenuItemDto menuItemDto, @PathVariable Integer id) {
        log.info("Updating menu item {} with data: {}", id, menuItemDto);
        service.update(menuItemDto, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Deleting menu item {}", id);
        service.delete(id);
    }
}
