package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.Category;
import ru.job4j.job4j_todo.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public List<Category> getCategoryListByIds(List<Integer> ids) {
        return categoryRepository.getCategoryListById(ids);
    }
}
