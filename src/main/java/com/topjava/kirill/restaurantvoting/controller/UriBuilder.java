package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.model.AbstractBaseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {

    public static URI buildFromEntity(AbstractBaseEntity created, String restUrl) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
    }
}
