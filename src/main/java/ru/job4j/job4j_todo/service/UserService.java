package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.repository.UserRepository;

import java.util.Optional;


/**
 * UserService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Add user.
     *
     * @param user User.
     * @return Optional.
     */
    public Optional<User> addUser(User user) {
        return this.userRepository.addUser(user);
    }

    /**
     * Get user by login and password.
     *
     * @param login    Login.
     * @param password Password.
     * @return Optional.
     */
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return this.userRepository.getUserByLoginAndPassword(login, password);
    }
}
