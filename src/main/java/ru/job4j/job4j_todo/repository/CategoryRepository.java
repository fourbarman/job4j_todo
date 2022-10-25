package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CategoryRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
@Repository
@AllArgsConstructor
public class CategoryRepository {
    private final CrudRepository crudRepository;

    /**
     * Get all categories.
     *
     * @return Category list.
     */
    public List<Category> getAllCategories() {
        return crudRepository.query("from Category", Category.class);
    }

    /**
     * Find category by id.
     *
     * @param id Category id.
     * @return Optional.
     */
    public Optional<Category> findCategoryById(int id) {
        return crudRepository.optional("from Category where id = :fId", Category.class, Map.of("fId", id));
    }
}
