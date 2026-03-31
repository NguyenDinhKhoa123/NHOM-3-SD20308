package poly.cafe.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        // 1. Vùng xanh: Không cần đăng nhập
        if (uri.equals(contextPath + "/") || uri.contains("/home") || uri.contains("/login") ||
                uri.contains("/drinks") || uri.contains("/assets") || uri.contains("/images")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Vùng đỏ: Bắt buộc đăng nhập (Khách, Nhân viên, Admin)
        if (uri.contains("/admin") || uri.contains("/my-orders") || uri.contains("/purchase")) {
            if (!AuthUtil.isAuthenticated(req)) {
                resp.sendRedirect(contextPath + "/login");
                return;
            }
        }

        // 3. Phân quyền chi tiết (Giải quyết lỗi 10 & 11)
        // Những trang CHỈ dành cho Admin
        if (uri.contains("/admin/dashboard") || uri.contains("/admin/drinks")) {
            if (!AuthUtil.isAdmin(req)) {
                // Nếu là STAFF thì đá về trang quản lý đơn hàng hoặc báo lỗi
                resp.sendError(403, "Bạn không có quyền truy cập khu vực Dashboard/Quản lý món!");
                return;
            }
        }

        // Mặc định: Cho phép đi tiếp (Ví dụ: Staff vào /admin/orders sẽ OK)
        chain.doFilter(request, response);
    }
}