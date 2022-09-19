package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.User;
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
    private final SessionFactory sf;

    public User addUser(User user) {
        try(Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            user = null;
        }
        return user;
    }


    public User getUserByLoginAndPassword(String login, String password) {
        User user;
        try(Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User where login = :fLogin and password = :fPassword")
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password);
            session.getTransaction().commit();
            user = (User)query.uniqueResult();
        }
        return user;
    }
}
