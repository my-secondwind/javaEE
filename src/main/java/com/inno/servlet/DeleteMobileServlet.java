package com.inno.servlet;

import com.inno.dao.MobileDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DeleteMobileServlet
 *
 * @author Ekaterina Belolipetskaya
 */
@WebServlet("/deletemobile")
public class DeleteMobileServlet extends HttpServlet {

    private MobileDao mobileDao;

    @Override
    public void init() throws ServletException {
        mobileDao = (MobileDao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mobileId = req.getParameter("id");
        if (mobileId == null) {
            throw new ServletException("Missing parameter id");
        }
        boolean result = mobileDao.deleteMobileById(Integer.valueOf(mobileId));
        if (!result) {
            throw new ServletException("SQLException occurs during deleting mobile");
        }
        resp.sendRedirect(req.getContextPath() + "/allmobiles");
    }

}
