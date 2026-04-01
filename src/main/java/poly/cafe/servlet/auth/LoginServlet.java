package poly.cafe.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.User;
import poly.cafe.service.UserService;
import poly.cafe.service.impl.UserServiceImpl;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("view", "/views/auth/login.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        User user = userService.login(email, pass);

        if (user != null) {
            AuthUtil.setUser(req, user);
            String role = user.getRole().toLowerCase();

            if (role.equals("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else if (role.equals("staff")) {
                resp.sendRedirect(req.getContextPath() + "/admin/orders");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("error", "Sai email hoặc mật khẩu!");
            req.setAttribute("view", "/views/auth/login.jsp");
            req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
        }
    }
}