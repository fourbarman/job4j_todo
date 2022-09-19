package ru.job4j.job4j_todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.service.TaskService;

import java.time.Instant;
/**
 * TaskController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.09.2022.
 */
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/index")
    public String index() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", this.taskService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Описание", Instant.now(), false));
        return "addTask";
    }

    @GetMapping("/completedTasks/{completed}")
    public String completedTasks(Model model, @PathVariable("completed") boolean completed) {
        model.addAttribute("tasks", this.taskService.getAllByComplete(completed));
        return "tasks";
    }

    @PostMapping("createTask")
    public String createTask(@RequestParam("desc") String desc) {
        this.taskService.addTask(new Task(0, desc, Instant.now(), false));
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskDetail/{taskId}")
    public String formTaskDetail(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", this.taskService.findTaskById(id));
        return "taskDetails";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", this.taskService.findTaskById(id));
        return "updateTask";
    }

    @PostMapping("updateTask")
    public String updateTask(@ModelAttribute Task task) {
        this.taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int id) {
        this.taskService.completeTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        this.taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
