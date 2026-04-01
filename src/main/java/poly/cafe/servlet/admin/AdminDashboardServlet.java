package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.dao.BillDAO;
import poly.cafe.dao.impl.BillDAOImpl;
import poly.cafe.dao.impl.StatisticDAOImpl;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.DrinkServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private BillDAO billDAO = new BillDAOImpl();
    private DrinkService drinkService = new DrinkServiceImpl();
    private StatisticDAOImpl statsDAO = new StatisticDAOImpl(); // Thêm DAO thống kê

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // ================= 1. DỮ LIỆU TỔNG QUAN (THẺ KPI) =================
            req.setAttribute("totalRevenue", billDAO.getTotalRevenue());
            req.setAttribute("totalOrders", billDAO.countTotalOrders());
            req.setAttribute("totalDrinks", drinkService.countPages(null, null, null, 1) > 0 ?
                    drinkService.search(null, null, null, 1, 1000).size() : 0);

            // ================= 2. DỮ LIỆU BIỂU ĐỒ & TOP 5 =================
            String start = req.getParameter("startDate");
            String end = req.getParameter("endDate");

            // Mặc định lấy 30 ngày gần nhất
            if (start == null || end == null) {
                end = LocalDate.now().toString();
                start = LocalDate.now().minusDays(30).toString();
            }

            req.setAttribute("topDrinks", statsDAO.getTop5Drinks(start, end));
            req.setAttribute("revenues", statsDAO.getRevenueByDate(start, end));
            req.setAttribute("startDate", start);
            req.setAttribute("endDate", end);

            // Gửi sang trang Dashboard
            req.setAttribute("view", "/views/admin/dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}