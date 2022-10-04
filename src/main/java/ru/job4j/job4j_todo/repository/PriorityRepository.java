package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PriorityRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 04.10.2022.
 */
@AllArgsConstructor
@Repository
public class PriorityRepository {
    private final CrudRepository crudRepository;

    /**
     * Get all priorities.
     *
     * @return List.
     */
    public List<Priority> getAllPriorities() {
        return this.crudRepository.query("from Priority", Priority.class);
    }

    /**
     * Get prioritiy by id.
     *
     * @param id Priority id.
     * @return Optional.
     */
    public Optional<Priority> getPriorityById(int id) {
        return this.crudRepository.optional("from Priority where id = :fId", Priority.class, Map.of("fId", id));
    }
}
