package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.repository.UserRepository;


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

    public User addUser(User user) {
        return this.userRepository.addUser(user);
    }

    public User getUserByLoginAndPassword(String login, String password) {
        return this.userRepository.getUserByLoginAndPassword(login, password).get();
    }
}
