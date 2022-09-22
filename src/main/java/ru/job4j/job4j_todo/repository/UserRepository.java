package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@AllArgsConstructor
@Repository
public class UserRepository {
    private final CrudRepository crudRepository;

    /**
     * addUser.
     *
     * @param user user to add.
     * @return User.
     */
    public User addUser(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * update.
     *
     * @param user user to update..
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * delete.
     *
     * @param userId User id.
     */
    public void delete(int userId) {
        crudRepository.run("delete from User where id = :fId", Map.of("fId", userId));
    }

    /**
     * findALLOrderById.
     *
     * @return List.
     */
    public List<User> findALLOrderById() {
        return crudRepository.query("from User", User.class);
    }

    /**
     * findById.
     *
     * @param userId User id.
     * @return List.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional("from User where id = :fId", User.class, Map.of("fId", userId));
    }

    /**
     * findByLikeLogin.
     *
     * @param key Searching key.
     * @return List.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query("from User where login like :fKey", User.class, Map.of("fKey", "%" + key + "%"));
    }

    /**
     * findByLogin.
     *
     * @param login User login.
     * @return Optional of user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional("from User where login = :fLogin", User.class, Map.of("fLogin", login));
    }

    /**
     * getUserByLoginAndPassword.
     *
     * @param login    Completed or not.
     * @param password Completed or not.
     * @return Optional of user.
     */
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return crudRepository.optional("from User where login = :fLogin and password = :fPassword",
                User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }
}
