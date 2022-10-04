package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.Priority;
import ru.job4j.job4j_todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

/**
 * PriorityService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 04.10.2022.
 */
@AllArgsConstructor
@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;

    /**
     * Get all priorities.
     *
     * @return List.
     */
    public List<Priority> getAllPriorities() {
        return this.priorityRepository.getAllPriorities();
    }

    /**
     * Get priority by id.
     *
     * @param id Priority id.
     * @return Optional.
     */
    public Optional<Priority> getPriorityById(int id) {
        return this.priorityRepository.getPriorityById(id);
    }
}
