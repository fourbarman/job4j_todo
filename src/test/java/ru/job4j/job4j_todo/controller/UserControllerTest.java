package ru.job4j.job4j_todo.controller;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UserControllerTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
public class UserControllerTest {

    /**
     * Test when loginPage.
     */
    @Test
    public void whenLoginPage() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        boolean fail = true;
        String page = userController.loginPage(model, fail);
        assertThat(page).isEqualTo("login");
    }

    /**
     * Test when login with valid username and password than redirect to index and success.
     */
    @Test
    public void whenLogin() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "username", "login", "pass", "UTC");
        when(userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword()))
                .thenReturn(Optional.of(user));
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, req);
        assertThat(page).isEqualTo("redirect:/index");
    }
    /**
     * Test when login with invalid username and password than fail and redirect to loginPage.
     */
    @Test
    public void whenLoginWithInvalidLoginPass() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "username", "login", "pass", "UTC");
        when(userService.getUserByLoginAndPassword("invalid login", "invalid pass"))
                .thenReturn(Optional.of(user));
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, req);
        assertThat(page).isEqualTo("redirect:/loginPage?fail=true");
    }

    /**
     * Test
     */
    @Test
    public void whenRegisterForm() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        boolean fail = true;
        String page = userController.registerForm(model, fail);
        assertThat(page).isEqualTo("register");
    }

    /**
     * Test register when register success.
     */
    @Test
    public void whenRegisterSuccess() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        User user = new User(1, "username", "login", "pass", "UTC");
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.register(user, req);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Test register when register fail than redirect to register.
     */
    @Test
    public void whenRegisterFail() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        User user = new User(1, "username", "login", "pass", "UTC");
        when(userService.addUser(user)).thenReturn(Optional.empty());
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.register(user, req);
        assertThat(page).isEqualTo("redirect:/register?fail=true");
    }
    /**
     * Test logout when logout than redirect to loginPage.
     */
    @Test
    public void whenLogout() {
        UserService userService = mock(UserService.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", "user");
        UserController userController = new UserController(userService);
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo("redirect:/loginPage");
    }
}
