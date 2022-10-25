package ru.job4j.job4j_todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.job4j_todo.model.Category;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * CategoryRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
public class CategoryRepositoryTest {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private static final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private static final CategoryRepository categoryRepository = new CategoryRepository(crudRepository);

    /**
     * Test getAllCategories when get all categories than return list and success.
     */
    @Test
    public void whenGetAllCategories() {
        List<Category> categories = categoryRepository.getAllCategories();
        assertThat(categories).isNotEmpty();
        assertThat(categories.size()).isEqualTo(8);
    }

    /**
     * Test findCategoryById when find category by id than return found category and success.
     */
    @Test
    public void whenFindCategoryById() {
        Category stored = categoryRepository.getAllCategories().get(0);
        Optional<Category> found = categoryRepository.findCategoryById(stored.getId());
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(stored);
    }
}
