package ru.job4j.job4j_todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_todo.model.Category;
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
        List<Task> list = taskService.getAllTasks();
        model.addAttribute("tasks", list);
        return "tasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.getAllPriorities());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addTask";
    }

    @GetMapping("/completedTasks/{completed}")
    public String completedTasks(Model model,
                                 @PathVariable("completed") boolean completed) {
        model.addAttribute("tasks", taskService.getAllByComplete(completed));
        return "tasks";
    }

    @PostMapping("createTask")
    public String createTask(@RequestParam("desc") String desc,
                             @RequestParam("priority.id") int priorityId,
                             @RequestParam List<Integer> categories,
                             HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        Priority priority = priorityService.getPriorityById(priorityId).get();
        List<Category> cats = categoryService.getCategoryListByIds(categories);
        taskService.addTask(new Task(0, desc, Instant.now(), false, user, priority, cats));
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskDetail/{taskId}")
    public String formTaskDetail(Model model,
                                 @PathVariable("taskId") int id,
                                 @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("success", success != null);
        Optional<Task> task = taskService.findTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("categories", task.get().getCategories());
        }
        return "taskDetails";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        Optional<Task> task = taskService.findTaskById(id);
        List<Priority> priorities = priorityService.getAllPriorities();
        List<Category> categories = categoryService.getAllCategories();
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("priorities", priorities);
            model.addAttribute("categories", categories);
        }
        return "updateTask";
    }

    @PostMapping("updateTask")
    public String updateTask(@RequestParam("id") int id,
                             @RequestParam("description") String desc,
                             @RequestParam("priority.id") int priorityId,
                             @RequestParam List<Integer> categories) {
        List<Category> cats = categoryService.getCategoryListByIds(categories);
        Task task = taskService.findTaskById(id).get();
        task.setDescription(desc);
        task.setPriority(priorityService.getPriorityById(priorityId).get());
        task.setCategories(cats);
        taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int id) {
        boolean success = taskService.completeTask(id);
        String failUrl = "redirect:/formTaskDetail/" + id + "?success=false";
        String successUrl= "redirect:/formTaskDetail/" + id + "?success=true";
        if (success) {
            return successUrl;
        }
        return failUrl;
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
