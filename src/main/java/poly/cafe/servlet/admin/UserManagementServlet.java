package poly.cafe.servlet.admin;

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
import java.util.List;

@WebServlet({
        "/admin/users",
        "/admin/users/create",
        "/admin/users/update",
        "/admin/users/edit",
        "/admin/users/delete"
})
public class UserManagementServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            if (path.contains("/edit")) {
                Long id = Long.parseLong(req.getParameter("id"));
                User user = userService.findById(id);
                req.setAttribute("userForm", user);

            } else if (path.contains("/delete")) {
                Long id = Long.parseLong(req.getParameter("id"));
                userService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/users?message=deleted");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String keyword = req.getParameter("keyword");

        // nếu bạn muốn chỉ tìm nhân viên → để "staff"
        // nếu muốn tìm tất cả → để null
        String role = "staff";

        List<User> users = userService.search(keyword, role);

        req.setAttribute("users", users);
        req.setAttribute("keyword", keyword);

        req.setAttribute("view", "/views/admin/user-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // PHẢI CÓ DÒNG NÀY ĐỂ KHÔNG LỖI TIẾNG VIỆT
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            String idStr = req.getParameter("id");
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String role = req.getParameter("role");

            if (fullname == null || fullname.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin/users?error=missing_info");
                return;
            }

            if (idStr == null || idStr.trim().isEmpty()) {
                // TẠO MỚI
                User user = new User();
                user.setFullname(fullname);
                user.setEmail(email);
                user.setPassword(password); // Nên mã hóa mật khẩu ở đây nếu có thể
                user.setPhone(phone);
                user.setRole(role != null ? role : "customer");
                user.setActive(true);

                userService.register(user);
            } else {
                // CẬP NHẬT
                Long id = Long.parseLong(idStr);
                User user = userService.findById(id);
                if (user != null) {
                    user.setFullname(fullname);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setRole(role);
                    if (password != null && !password.trim().isEmpty()) {
                        user.setPassword(password);
                    }
                    userService.update(user);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/users?error=system_error");
        }
    }
}