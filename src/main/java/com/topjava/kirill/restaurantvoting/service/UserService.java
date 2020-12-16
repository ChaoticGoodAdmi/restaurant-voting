package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.dto.UserDto;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.repository.UserRepository;
import com.topjava.kirill.restaurantvoting.util.UserUtil;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        log.info("Getting all users");
        return repository.findAll();
    }

    public User get(int id) {
        log.info("Getting user with id {}", id);
        return checkNotFoundWithId(repository.findById(id).orElseThrow(
                () -> new NotFoundException("User data not found")), id);
    }

    public User getByEmail(String email) {
        log.info("Getting user with email {}", email);
        return repository.getByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Email + " + email + " not found"));
    }

    public void delete(int id) {
        log.info("Deleting user with id {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User create(UserDto userDto) {
        checkNew(userDto);
        log.info("Creating user with id {}", userDto.getId());
        User user = UserUtil.createFromDto(userDto);
        return repository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    public User create(User user) {
        checkNew(user);
        log.info("Creating user with id {}", user.getId());
        return repository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    public void update(UserDto userDto, Integer id) {
        assureEntityIdConsistent(userDto, id);
        Integer userId = userDto.getId();
        log.info("Updating user with id {}", userId);
        User user = UserUtil.prepareToUpdate(get(userId), userDto);
        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        return new AuthorizedUser(user);
    }
}
