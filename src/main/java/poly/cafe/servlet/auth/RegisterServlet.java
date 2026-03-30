package poly.cafe.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.User;
import poly.cafe.service.UserService;
import poly.cafe.service.impl.UserServiceImpl;
import poly.cafe.utils.SendMail;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "/views/auth/register.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullname(fullname);
            user.setPhone(phone);
            user.setRole("customer");
            user.setActive(true);
            user.setCreatedAt(LocalDateTime.now());

            User result = userService.register(user);

            if (result != null) {
                // 🚀 THÊM GỬI MAIL TẠI ĐÂY (Dùng Thread chạy ngầm)
                new Thread(() -> {
                    SendMail.sendWelcomeEmail(result.getEmail(), result.getFullname());
                }).start();

                resp.sendRedirect(req.getContextPath() + "/login?message=register-success");
            } else {
                req.setAttribute("error", "Email này đã tồn tại!");
                req.setAttribute("view", "/views/auth/register.jsp");
                req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            req.setAttribute("view", "/views/auth/register.jsp");
            req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
        }
    }
}