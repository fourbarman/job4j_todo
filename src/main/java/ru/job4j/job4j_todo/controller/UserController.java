package ru.job4j.job4j_todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * UserController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@AllArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/users/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "/users/login";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> foundUser = this.userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (foundUser.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", foundUser.get());
        return "redirect:/index";
    }

    @GetMapping("/users/register")
    public String registerForm(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("timezones", userService.getTimeZones());
        return "/users/register";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> addedUser = this.userService.addUser(user);
        if (addedUser.isEmpty()) {
            return "redirect:/users/register?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", addedUser.get());
        return "redirect:/index";
    }

    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/loginPage";
    }
}
