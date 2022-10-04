package ru.job4j.job4j_todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_todo.model.Priority;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.service.PriorityService;
import ru.job4j.job4j_todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
    private final PriorityService priorityService;

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
    public String addTask(Model model, HttpSession httpSession) {
        //User user = (User)httpSession.getAttribute("user");
        model.addAttribute("priorities", this.priorityService.getAllPriorities());
//        model.addAttribute("task", new Task(0, "Описание", Instant.now(), false, user, null));///////
        return "addTask";
    }

    @GetMapping("/completedTasks/{completed}")
    public String completedTasks(Model model, @PathVariable("completed") boolean completed) {
        model.addAttribute("tasks", this.taskService.getAllByComplete(completed));
        return "tasks";
    }

    @PostMapping("createTask")
    public String createTask(@RequestParam("desc") String desc, @RequestParam("priority.id") int priorityId, HttpSession httpSession) {
        Priority priority = this.priorityService.getPriorityById(priorityId).get();
        System.out.println("CONTROLLER: " + priority);
        User user = (User)httpSession.getAttribute("user");
        this.taskService.addTask(new Task(0, desc, Instant.now(), false, user, priority));
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskDetail/{taskId}")
    public String formTaskDetail(Model model, @PathVariable("taskId") int id) {
        Optional<Task> task = this.taskService.findTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        }
        return "taskDetails";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        Optional<Task> task = this.taskService.findTaskById(id);
        List<Priority> priorities = this.priorityService.getAllPriorities();
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("priorities", priorities);
        }
        return "updateTask";
    }

    @PostMapping("updateTask")
    public String updateTask(@ModelAttribute Task task, HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        task.setUser(user);
        System.out.println("CONTROLLER POST updateTask: " + task);
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
