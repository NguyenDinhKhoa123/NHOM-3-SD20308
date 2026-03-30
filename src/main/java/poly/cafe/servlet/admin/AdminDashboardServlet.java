package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.dao.BillDAO;
import poly.cafe.dao.impl.BillDAOImpl;
import poly.cafe.dao.DrinkDAO;
import poly.cafe.dao.impl.DrinkDAOImpl;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private BillDAO billDAO = new BillDAOImpl();
    private DrinkDAO drinkDAO = new DrinkDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy các con số thống kê
        req.setAttribute("totalRevenue", billDAO.getTotalRevenue());
        req.setAttribute("totalOrders", billDAO.countTotalOrders());
        req.setAttribute("totalDrinks", drinkDAO.findAll().size()); // Trả về 50 món của bạn

        req.setAttribute("view", "/views/admin/dashboard.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}