package com.topjava.kirill.restaurantvoting;

import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserDto userDto;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getRoles());
    }

    public void update(UserDto newTo) {
        userDto = newTo;
    }

    public Integer getId() {
        return userDto.getId();
    }
}