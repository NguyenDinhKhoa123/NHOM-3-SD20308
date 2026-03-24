package poly.cafe.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet({"/home", "/admin/dashboard", "/staff/orders","/"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("/admin/")) {
            req.setAttribute("title", "Quản trị");
            req.setAttribute("type", "ADMIN");
        } else if (uri.contains("/staff/")) {
            req.setAttribute("title", "Nhân viên");
            req.setAttribute("type", "STAFF");
        } else {
            req.setAttribute("title", "Trang chủ");
            req.setAttribute("type", "GUEST");
        }

        // CHỈ CẦN 1 FILE NÀY LÀ ĐỦ
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}