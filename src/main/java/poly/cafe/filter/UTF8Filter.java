package poly.cafe.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter này đảm bảo mọi Request và Response đều sử dụng bảng mã UTF-8
 * Giúp hiển thị tiếng Việt chính xác trên giao diện và khi lưu vào Database.
 */
@WebFilter(filterName = "UTF8Filter", urlPatterns = "/*") // Áp dụng cho TOÀN BỘ các trang
public class UTF8Filter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        // 1. Thiết lập encoding cho dữ liệu gửi lên (Request)
        req.setCharacterEncoding("UTF-8");

        // 2. Thiết lập encoding cho dữ liệu trả về (Response)
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        // 3. Cho phép request đi tiếp đến các Filter khác hoặc Servlet
        chain.doFilter(req, resp);
    }
}