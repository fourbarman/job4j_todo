package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Category;

import java.util.List;
import java.util.Map;

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
     * Get category list by list of ids.
     *
     * @param ids List of id.
     * @return Category list.
     */
    public List<Category> getCategoryListById(List<Integer> ids) {
        return crudRepository.query("from Category c where c.id in (:fIds)", Category.class, Map.of("fIds", ids));
    }
}
