package ru.job4j.job4j_todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.job4j_todo.model.Priority;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * PriorityRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
public class PriorityRepositoryTest {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private static final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private static final PriorityRepository priorityRepository = new PriorityRepository(crudRepository);

    /**
     * Test getAllPriorities when get all than return all and success.
     */
    @Test
    public void whenGetAllPropertiesThanReturnAll() {
        List<Priority> priorities = priorityRepository.getAllPriorities();
        assertThat(priorities).isNotEmpty();
        assertThat(priorities.size()).isEqualTo(4);
    }

    /**
     * Test getPriorityById when get priority by id than return found priority and success.
     */
    @Test
    public void whenGetPriorityByIdThanReturnPriority() {
        Priority stored = priorityRepository.getAllPriorities().get(0);
        Optional<Priority> found = priorityRepository.getPriorityById(stored.getId());
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(stored);
    }
}
