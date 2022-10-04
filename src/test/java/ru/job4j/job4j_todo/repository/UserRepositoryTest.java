package ru.job4j.job4j_todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.job4j_todo.model.User;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private static final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private static final UserRepository userRepository = new UserRepository(crudRepository);

    @Test
    public void whenAddUserThanSuccess() {
        User user = new User(0, "testUser1", "testLogin1", "testPass1");
        Optional<User> addedUser = userRepository.addUser(user);
        assertThat(addedUser).isPresent();
        assertThat(addedUser.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenGetUserByLoginAndPassword() {
        User user2 = new User(0, "testUser2", "testLogin2", "testPass2");
        User user3 = new User(0, "testUser3", "testLogin3", "testPass3");
        userRepository.addUser(user2);
        userRepository.addUser(user3);
        Optional<User> addedUser2 = userRepository.getUserByLoginAndPassword(user2.getLogin(), user2.getPassword());
        Optional<User> addedUser3 = userRepository.getUserByLoginAndPassword(user3.getLogin(), user3.getPassword());
        assertThat(addedUser2).isPresent();
        assertThat(addedUser3).isPresent();
        assertThat(addedUser2.get().getLogin()).isEqualTo(user2.getLogin());
        assertThat(addedUser3.get().getLogin()).isEqualTo(user3.getLogin());
    }
}
