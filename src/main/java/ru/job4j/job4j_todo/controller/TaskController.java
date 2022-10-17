package ru.job4j.job4j_todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_todo.model.Priority;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.service.CategoryService;
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

    private final CategoryService categoryService;

    @GetMapping("/index")
    public String index() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        List<Task> list = this.taskService.getAllTasks();
        model.addAttribute("tasks", list);
        //List<>
        //model.addAttribute("categories", this.categoryService.getAllCategories());
        for (Task t : list) {
            System.out.println(t);
        }
        return "tasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession httpSession) {
        model.addAttribute("priorities", this.priorityService.getAllPriorities());
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "addTask";
    }

    @GetMapping("/completedTasks/{completed}")
    public String completedTasks(Model model, @PathVariable("completed") boolean completed) {
        model.addAttribute("tasks", this.taskService.getAllByComplete(completed));
        return "tasks";
    }

    @PostMapping("createTask")
    public String createTask(@RequestParam("desc") String desc,
                             @RequestParam("priority.id") int priorityId, HttpSession httpSession) {
        Priority priority = this.priorityService.getPriorityById(priorityId).get();
        User user = (User)httpSession.getAttribute("user"); //TODO select categories option on form !
        this.taskService.addTask(new Task(0, desc, Instant.now(), false, user, priority, null));
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskDetail/{taskId}")
    public String formTaskDetail(Model model,
                                 @PathVariable("taskId") int id,
                                 @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("success", success != null);
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
        this.taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/completeTask/{taskId}")
    public String completeTask(Model model, @PathVariable("taskId") int id) {
        boolean success = this.taskService.completeTask(id);
        String failUrl = "redirect:/formTaskDetail/" + id + "?success=false";
        String successUrl= "redirect:/formTaskDetail/" + id + "?success=true";
        if (success) {
            return successUrl;
        }
        return failUrl;
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        this.taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
