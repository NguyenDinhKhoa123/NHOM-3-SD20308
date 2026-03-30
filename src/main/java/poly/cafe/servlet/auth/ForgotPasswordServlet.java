package poly.cafe.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.User;
import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl;
import poly.cafe.utils.SendMail;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "/views/auth/forgot-password.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userDAO.findByEmail(email);

        if (user != null) {
            // 1. Tạo mật khẩu mới ngẫu nhiên (cắt lấy 6 ký tự)
            String newPassword = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            // 2. Cập nhật mật khẩu mới vào Database
            user.setPassword(newPassword);
            userDAO.update(user); // GenericDAO đã có sẵn hàm update()

            // 3. 🚀 Gửi mật khẩu mới qua Email (Dùng Thread chạy ngầm)
            new Thread(() -> {
                SendMail.sendPasswordEmail(user.getEmail(), newPassword);
            }).start();

            req.setAttribute("message", "Mật khẩu mới đã được gửi vào email của bạn. Vui lòng kiểm tra hộp thư!");
        } else {
            req.setAttribute("error", "Email không tồn tại trong hệ thống!");
        }

        req.setAttribute("view", "/views/auth/forgot-password.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}