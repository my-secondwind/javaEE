package com.inno.servlet;

import com.inno.dao.MobileDao;
import com.inno.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * AppContextListener
 *
 * @author Ekaterina Belolipetskaya
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(AppContextListener.class);
    @Inject
    private MobileDao mobileDao;
    @Inject
    private UserDao userDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dao", mobileDao);
        servletContext.setAttribute("userDao", userDao);
        logger.debug("Added attribute DAO");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.removeAttribute("dao");
        servletContext.removeAttribute("userDao");
        logger.debug("Removed attribute DAO");
    }
}
