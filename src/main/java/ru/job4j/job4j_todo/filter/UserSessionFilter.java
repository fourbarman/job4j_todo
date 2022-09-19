package ru.job4j.job4j_todo.filter;

import ru.job4j.job4j_todo.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * UserSessionFilter.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@Component
@Order(1)
public class UserSessionFilter implements Filter {
    /**
     * doFilter.
     * Filter user from session.
     * If user in request is null than set user to "Гость" and return it to request.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        req.setAttribute("user", user);
        chain.doFilter(req, res);
    }
}
