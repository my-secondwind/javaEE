package com.inno.servlet;

import com.inno.dao.UserDao;
import com.inno.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginFilter
 *
 * @author Ekaterina Belolipetskaya
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Init loginFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        userDao = (UserDao) req.getServletContext().getAttribute("userDao");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        User user = new User();
        user.setEmail(login);
        user.setPassword(password);

        if ((session != null) &&
                (session.getAttribute("login") != null) &&
                (session.getAttribute("password") != null)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if ((session != null) && (userDao.verifyUser(user))) {
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            resp.sendRedirect(req.getRequestURI());
        } else {
            req.setAttribute("PageTitle", "Login Page");
            req.setAttribute("PageBody", "login.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        logger.debug("Destroy loginFilter");
    }
}
