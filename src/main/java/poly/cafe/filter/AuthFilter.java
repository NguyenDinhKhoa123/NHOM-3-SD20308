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
        String ctx = req.getContextPath();

        // Vùng công khai: không cần đăng nhập
        if (uri.equals(ctx + "/") || uri.contains("/home") || uri.contains("/login") ||
                uri.contains("/drinks") || uri.contains("/assets") || uri.contains("/images")) {
            chain.doFilter(request, response);
            return;
        }

        // Yêu cầu đăng nhập
        if (uri.contains("/admin") || uri.contains("/my-orders") || uri.contains("/purchase")) {
            if (!AuthUtil.isAuthenticated(req)) {
                resp.sendRedirect(ctx + "/login");
                return;
            }
        }

        // Chỉ Admin mới được vào dashboard và quản lý món
        if (uri.contains("/admin/dashboard") || uri.contains("/admin/drinks")) {
            if (!AuthUtil.isAdmin(req)) {
                resp.sendError(403, "Bạn không có quyền truy cập khu vực này!");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}