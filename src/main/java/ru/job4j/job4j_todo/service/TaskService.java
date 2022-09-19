package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.repository.TaskRepository;

import java.util.List;
/**
 * TaskService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return this.taskRepository.getAllTasks();
    }

    public List<Task> getAllByComplete(boolean completed) {
        return this.taskRepository.getAllByComplete(completed);
    }

    public Task addTask(Task newTask) {
        return this.taskRepository.addTask(newTask);
    }

    public Task findTaskById(int id) {
        return this.taskRepository.findTaskById(id);
    }

    public void updateTask(Task task) {
        this.taskRepository.updateTask(task);
    }

    public void completeTask(int id) {
        Task task = this.taskRepository.findTaskById(id);
        task.setDone(true);
        this.taskRepository.updateTask(task);
    }

    public void deleteTask(int id) {
        Task task = this.taskRepository.findTaskById(id);
        this.taskRepository.deleteTask(task);
    }
}
