package ru.job4j.job4j_todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TaskControllerTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
public class TaskControllerTest {

    /**
     * Test index when index than return tasks page.
     */
    @Test
    public void whenIndexThanReturnTasksPage() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.index();
        assertThat(page).isEqualTo("redirect:/tasks");
    }

    /**
     * Test tasks when tasks than model has task list and return tasks page.
     */
    @Test
    public void whenTasksThanModelHasTaskListAndReturnTasksPage() {
        List<Task> tasks = List.of(
                new Task(1, "desc1", Instant.now(), false, new User(), new Priority(), List.of(new Category(), new Category())),
                new Task(2, "desc2", Instant.now(), false, new User(), new Priority(), List.of(new Category(), new Category()))
        );
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.getAllTasks()).thenReturn(tasks);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.tasks(model);
        verify(model).addAttribute("tasks", tasks);
        assertThat(page).isEqualTo("tasks");
    }

    /**
     * Test addTask when add task than model has task list, priority list, category list and return addTask page.
     */
    @Test
    public void whenAddTask() {
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        List<Priority> priorityList = List.of(new Priority(), new Priority());
        List<Category> categoryList = List.of(new Category(), new Category());
        when(priorityService.getAllPriorities()).thenReturn(priorityList);
        when(categoryService.getAllCategories()).thenReturn(categoryList);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.addTask(model);
        verify(model).addAttribute("priorities", priorityList);
        verify(model).addAttribute("categories", categoryList);
        verify(model).addAttribute("task", new Task());
        assertThat(page).isEqualTo("addTask");
    }

    /**
     * Test completedTasks when completedTasks with true than model has completed task list and return tasks page.
     */
    @Test
    public void whenCompletedTasksAndTrueThanReturnCompletedTasksAndReturnTasksPage() {
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        List<Task> completedTasks = List.of(new Task(), new Task());
        when(taskService.getAllByComplete(true)).thenReturn(completedTasks);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String completedPage = taskController.completedTasks(model, true);
        verify(model).addAttribute("tasks", completedTasks);
        assertThat(completedPage).isEqualTo("tasks");
    }

    /**
     * Test completedTasks when completedTasks with false than model has uncompleted task list and return taksks page.
     */
    @Test
    public void whenCompletedTasksAndFalseThanReturnUncompletedTasksAndReturnTasksPage() {
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        List<Task> uncompletedTasks = List.of(new Task(), new Task());
        when(taskService.getAllByComplete(false)).thenReturn(uncompletedTasks);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String openedPage = taskController.completedTasks(model, false);
        verify(model).addAttribute("tasks", uncompletedTasks);
        assertThat(openedPage).isEqualTo("tasks");
    }

    /**
     * Test createTask when createTask than return "redirect:/tasks";
     */
    @Test
    public void whenCreateTask() {
        User user = new User();
        Priority priority = new Priority();
        List<Category> categories = List.of(new Category(1, "cat1"), new Category(2, "cat2"));
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user")).thenReturn(user);
        PriorityService priorityService = mock(PriorityService.class);
        when(priorityService.getPriorityById(1)).thenReturn(Optional.of(priority));
        CategoryService categoryService = mock(CategoryService.class);
        when(categoryService.getAllCategories()).thenReturn(categories);
        TaskService taskService = mock(TaskService.class);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.createTask("description", 1, List.of(1), httpSession);
        assertThat(page).isEqualTo("redirect:/tasks");
    }

    /**
     * Test taskDetails when taskDetails then return taskDetails page.
     */
    @Test
    public void whenFormTaskDetail() {
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        Task task = new Task();
        when(taskService.findTaskById(anyInt())).thenReturn(Optional.of(task));
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.formTaskDetail(model, 1, true);
        verify(model).addAttribute("success", true);
        verify(model).addAttribute("task", task);
        assertThat(page).isEqualTo("taskDetails");
    }

    /**
     * Test fromUpdateTask when formUpdateTask than model has task, priorities, categories.
     */
    @Test
    public void whenFormUpdateTask() {
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        Task task = new Task();
        List<Priority> priorities = List.of(new Priority(), new Priority());
        List<Category> categories = List.of(new Category(), new Category());
        when(taskService.findTaskById(anyInt())).thenReturn(Optional.of(task));
        when(priorityService.getAllPriorities()).thenReturn(priorities);
        when(categoryService.getAllCategories()).thenReturn(categories);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        taskController.formUpdateTask(model, 1);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("priorities", priorities);
        verify(model).addAttribute("categories", categories);
    }

    /**
     * Test updateTask when updateTask than redirect to tasks page.
     */
    @Test
    public void whenUpdateTask() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        Task task = new Task();
        Priority priority = new Priority();
        Category cat1 = new Category(1, "cat1");
        Category cat2 = new Category(2, "cat2");
        List<Category> categories = List.of(cat1, cat2);
        when(taskService.findTaskById(anyInt())).thenReturn(Optional.of(task));
        when(priorityService.getPriorityById(1)).thenReturn(Optional.of(priority));
        when(categoryService.getAllCategories()).thenReturn(categories);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String desc = "desc";
        String page = taskController.updateTask(1, desc, 1, List.of(1));
        assertThat(page).isEqualTo("redirect:/tasks");
        assertThat(task.getDescription()).isEqualTo(desc);
        assertThat(task.getCategories().size()).isEqualTo(1);
        assertThat(task.getCategories().get(0)).isEqualTo(cat1);
    }

    /**
     * Test completeTask when completeTask success than redirect to formTaskDetail page with success.
     */
    @Test
    public void whenCompleteTaskAndSuccessThanReturnSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.completeTask(anyInt())).thenReturn(true);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        int id = 1;
        String page = taskController.completeTask(id);
        assertThat(page).isEqualTo("redirect:/formTaskDetail/" + id + "?success=true");
    }

    /**
     * Test completeTask when completeTask fail than redirect to formTaskDetail page with fail.
     */
    @Test
    public void whenCompleteTaskAndFailThanReturnFail() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.completeTask(anyInt())).thenReturn(false);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        int id = 1;
        String page = taskController.completeTask(id);
        assertThat(page).isEqualTo("redirect:/formTaskDetail/" + id + "?success=false");
    }

    /**
     * Test deleteTask when deleteTask than redirect to tasks page.
     */
    @Test
    public void whenDeleteTask() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        String page = taskController.deleteTask(1);
        assertThat(page).isEqualTo("redirect:/tasks");
    }
}
