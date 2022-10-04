package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    //SessionFactory sessionFactory;
    private final CrudRepository crudRepository;

    /**
     * addUser.
     *
     * @param user user to add.
     * @return User.
     */
    public Optional<User> addUser(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(user);
//        try (Session session = sessionFactory.openSession()) {
//            session.persist(user);
//        } catch (Exception e) {
//            //
//            e.printStackTrace();
//            System.out.println("EXCEPTION HERE!");
//            return Optional.empty();
//        }
//        return Optional.of(user);
    }

    /**
     * getUserByLoginAndPassword.
     *
     * @param login    Completed or not.
     * @param password Completed or not.
     * @return Optional of user.
     */
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
//        Session session = sessionFactory.openSession();
//        Query<User> query = session.createQuery("from User u where u.login = :fLogin and u.password = :fPassword", User.class);
//        query.setParameter("fLogin", login);
//        query.setParameter("fPassword", password);

        return this.crudRepository.optional("from User where login = :fLogin and password = :fPassword", User.class, Map.of("fLogin", login, "fPassword", password));
    }
}
