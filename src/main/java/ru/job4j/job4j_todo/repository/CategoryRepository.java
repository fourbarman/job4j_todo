package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {
    private final CrudRepository crudRepository;

    public List<Category> getAllCategories() {
        return crudRepository.query("from Category", Category.class);
    }

    public Optional<Category> findCategoryById(int id) {
        return crudRepository.optional("from Category", Category.class, Map.of("fId", id));
    }
}
