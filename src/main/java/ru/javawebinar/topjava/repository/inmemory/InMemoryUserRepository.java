package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete user id={}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            log.info("add user {}", user);
            return user;
        }
        // handle case: update, but not present in storage
        log.info("edit user {}", user);
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        repository.get(id);
        log.info("get user id={}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>(repository.values());
        Collections.sort(users, User.COMPARE_BY_ID);
        Collections.sort(users, User.COMPARE_BY_NAME);
        log.info("getAll {}", users);
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(n -> n.getEmail().equals(email)).findFirst().orElse(null);
    }
}
