package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.AuthorizedUser;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    private User getByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email + " + email + " not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        return new AuthorizedUser(user);
    }
}
