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
import java.util.List;
import java.util.NoSuchElementException;
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
        return "redirect:/tasks/tasks";
    }

    @GetMapping("/tasks/tasks")
    public String tasks(Model model,
                        @RequestParam(name = "notFound", required = false) Boolean notFound,
                        HttpSession httpSession) {
        model.addAttribute("notFound", notFound != null);
        User user = (User)httpSession.getAttribute("user");
        List<Task> list = taskService.getAllTasks(user);
        model.addAttribute("tasks", list);
        return "/tasks/tasks";
    }

    @GetMapping("/tasks/addTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.getAllPriorities());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/tasks/addTask";
    }

    @GetMapping("/tasks/completedTasks/{completed}")
    public String completedTasks(Model model,
                                 @PathVariable("completed") boolean completed) {
        model.addAttribute("tasks", taskService.getAllByComplete(completed));
        return "/tasks/tasks";
    }

    @PostMapping("/tasks/createTask")
    public String createTask(@RequestParam("desc") String desc,
                             @RequestParam("priority.id") int priorityId,
                             @RequestParam List<Integer> categories,
                             HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        Priority priority = priorityService.getPriorityById(priorityId).orElseThrow(
                () -> new NoSuchElementException("Priority id: " + priorityId)
        );
        List<Category> cats = categoryService.getCategoryListByIds(categories);
        taskService.addTask(desc, user, priority, cats);
        return "redirect:/tasks/tasks";
    }

    @GetMapping("/tasks/formTaskDetail/{taskId}")
    public String formTaskDetail(Model model,
                                 @PathVariable("taskId") int id,
                                 @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("success", success != null);
        Optional<Task> task = taskService.findTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("categories", task.get().getCategories());
        }
        return "/tasks/taskDetails";
    }

    @GetMapping("/tasks/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        Optional<Task> task = taskService.findTaskById(id);
        if (task.isEmpty()) {
            return "redirect:tasks/tasks?notFound=false";
        }
        List<Priority> priorities = priorityService.getAllPriorities();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("task", task.get());
        model.addAttribute("priorities", priorities);
        model.addAttribute("categories", categories);
        return "/tasks/updateTask";
    }

    @PostMapping("/tasks/updateTask")
    public String updateTask(@RequestParam("id") int id,
                             @RequestParam("description") String desc,
                             @RequestParam("priority.id") int priorityId,
                             @RequestParam List<Integer> categories) {
        List<Category> cats = categoryService.getCategoryListByIds(categories);
        Task task = taskService.findTaskById(id).orElseThrow(
                () -> new NoSuchElementException("Task id: " + id)
        );
        Priority priority = priorityService.getPriorityById(priorityId).orElseThrow(
                () -> new NoSuchElementException("Priority id: " + priorityId)
        );
        task.setPriority(priority);
        task.setDescription(desc);
        task.setCategories(cats);
        taskService.updateTask(task);
        return "redirect:/tasks/tasks";
    }

    @GetMapping("/tasks/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int id) {
        boolean success = taskService.completeTask(id);
        String failUrl = "redirect:/tasks/formTaskDetail/" + id + "?success=false";
        String successUrl= "redirect:/tasks/formTaskDetail/" + id + "?success=true";
        if (success) {
            return successUrl;
        }
        return failUrl;
    }

    @GetMapping("/tasks/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/tasks";
    }
}
