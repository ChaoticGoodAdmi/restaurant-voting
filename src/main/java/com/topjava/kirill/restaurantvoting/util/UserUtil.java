package com.topjava.kirill.restaurantvoting.util;

import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.Role;
import com.topjava.kirill.restaurantvoting.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Set;

public class UserUtil {

    public static User createFromDto(UserDto userDto) {
        return new User(null, userDto.getEmail(), userDto.getPassword(), Set.of(Role.ROLE_USER) );
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
}
