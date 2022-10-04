//package ru.job4j.job4j_todo.repository;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.junit.jupiter.api.Test;
//import ru.job4j.job4j_todo.model.User;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
///**
// * UserRepositoryTest.
// */
//public class UserRepositoryTest {
//    @Test
//    public void whenAddUser() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("hibernate-test.cfg.xml").build();
//        SessionFactory factory = new MetadataSources(registry)
//                .buildMetadata()
//                .buildSessionFactory();
//        CrudRepository crudRepository = new CrudRepository(factory);
//        UserRepository userRepository = new UserRepository(crudRepository);
//        User user = new User(0, "username1", "login", "pass1");
//        Optional<User> addedUser = userRepository.addUser(user);
//        assertThat(addedUser).isPresent();
//        assertThat(addedUser.get().getUsername()).isEqualTo(user.getUsername());
//    }
//
//    @Test
//    public void whenAddUserWithSameLogin() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("hibernate-test.cfg.xml").build();
//        SessionFactory factory = new MetadataSources(registry)
//                .buildMetadata()
//                .buildSessionFactory();
//        CrudRepository crudRepository = new CrudRepository(factory);
//        UserRepository userRepository = new UserRepository(crudRepository);
//        User user = new User(0, "username1", "login", "pass1");
//        Optional<User> addedUser = userRepository.addUser(user);
//        assertThat(addedUser).isEmpty();
//    }
//}
