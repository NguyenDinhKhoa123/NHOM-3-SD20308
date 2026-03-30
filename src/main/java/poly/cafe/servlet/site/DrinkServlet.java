package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Drink;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.DrinkServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/drinks")
public class DrinkServlet extends HttpServlet {
    private DrinkService drinkService = new DrinkServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy keyword search (nếu có)
        String keyword = req.getParameter("keyword");

        // 2. Lấy dữ liệu
        List<Drink> list = drinkService.search(keyword);

        // 3. Đẩy dữ liệu ra JSP
        req.setAttribute("drinks", list);

        // 4. Chỉ định file nội dung cho Layout
        req.setAttribute("view", "/views/site/drink-list.jsp");

        // 5. Chuyển hướng sang file Layout tổng
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}