package poly.cafe.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl; // Nhớ import Impl của bạn
import poly.cafe.entity.User;
import poly.cafe.utils.ParamUtil;
import java.io.IOException;

@WebServlet({"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    // Khởi tạo DAO (Nên dùng Singleton hoặc khởi tạo trong init)
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy dữ liệu từ form và trim() để xóa khoảng trắng thừa
        String email = ParamUtil.getString(req, "email", "").trim();
        String password = ParamUtil.getString(req, "password", "").trim();

        try {
            // 2. GỌI DATABASE THẬT QUA DAO
            User user = userDAO.login(email, password);

            if (user != null) {
                // Đăng nhập thành công
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                // Thất bại
                req.setAttribute("message", "Email hoặc mật khẩu không chính xác!");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi kết nối cơ sở dữ liệu!");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }
}