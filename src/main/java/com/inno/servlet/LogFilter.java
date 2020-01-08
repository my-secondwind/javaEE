package com.inno.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * LogFilter
 *
 * @author Ekaterina Belolipetskaya
 */
@WebFilter("/*")
public class LogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("init logFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String uri = "Requested Uri::" + req.getRequestURI();
        logger.debug(uri);
        String method = "Requested Method::" + req.getMethod();
        logger.debug(method);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("destroy logFilter");
    }
}
