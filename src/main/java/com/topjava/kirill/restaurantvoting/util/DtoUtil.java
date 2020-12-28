package com.topjava.kirill.restaurantvoting.util;

import com.topjava.kirill.restaurantvoting.dto.RestaurantDto;
import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.model.Role;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.model.Vote;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Set;

public class DtoUtil {

    public static User createFromDto(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail(), userDto.getPassword(), Set.of(Role.ROLE_USER) );
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static User prepareToUpdate(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public static RestaurantDto fromRestaurantOnDate(Restaurant restaurant, LocalDate date) {
        Set<Vote> votes = restaurant.getVotes();
        return new RestaurantDto(restaurant, date, votes.size());
    }
}
