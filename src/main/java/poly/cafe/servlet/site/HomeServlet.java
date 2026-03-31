package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home", "/", ""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đặt tiêu đề hoặc dữ liệu cần thiết cho trang chủ
        req.setAttribute("message", "Chào mừng đến với PolyCafe!");

        // Khai báo view để layout.jsp thực hiện include
        req.setAttribute("view", "/views/site/home.jsp");

        // Forward sang layout chính
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}