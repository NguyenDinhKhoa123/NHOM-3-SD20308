package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/", "/home"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Trang chủ có thể chưa cần load dữ liệu phức tạp
        req.setAttribute("message", "Chào mừng đến với PolyCafe!");

        // Đặt trang con là home.jsp để layout.jsp include vào
        req.setAttribute("view", "/views/site/home.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}