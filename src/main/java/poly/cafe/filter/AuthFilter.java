package poly.cafe.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter; // Tomcat 10 hỗ trợ rất tốt class này
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.cafe.entity.User;

// Chỉ chặn các URL cần bảo mật
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/admin/*", "/staff/*", "/profile/*"})
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)

            throws IOException, ServletException {

        // 1. Kiểm tra Session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String uri = req.getRequestURI();

        // 2. Chặn nếu chưa đăng nhập (Authentication)
        if (user == null) {
            // Chuyển hướng về trang login kèm thông báo
            resp.sendRedirect(req.getContextPath() + "/login?error=unauthorized");
            return;
        }

        // 3. Kiểm tra quyền hạn (Authorization)
        String role = user.getRole().toLowerCase(); // admin, staff, customer
        boolean isAuthorized = true;

        if (uri.contains("/admin/") && !role.equals("admin")) {
            isAuthorized = false;
        }
        else if (uri.contains("/staff/") && !(role.equals("staff") || role.equals("admin"))) {
            // Admin có quyền vào cả trang của Staff
            isAuthorized = false;
        }

        // 4. Xử lý kết quả
        if (!isAuthorized) {
            // Trả về lỗi 403 (Forbidden) nếu không đủ quyền
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen truy cap khu vuc nay!");
        } else {
            // Hợp lệ cho đi tiếp
            chain.doFilter(req, resp);
        }
    }
}