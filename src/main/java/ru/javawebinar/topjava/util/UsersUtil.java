package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import java.util.*;

public class UsersUtil {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        userRepository.getAll();
        userRepository.getByEmail("user@mail.com");
    }

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Admin", "admin@mail.com", "admin", Role.ROLE_ADMIN, Role.values()),
            new User(null, "User", "user@mail.com", "user", Role.ROLE_USER, Role.ROLE_USER),
            new User(null, "Admin", "admin@mail.com", "admin", Role.ROLE_ADMIN, Role.values())
    );
}
