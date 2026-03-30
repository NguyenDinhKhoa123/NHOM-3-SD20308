package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Bill;
import poly.cafe.dao.BillDAO;
import poly.cafe.dao.impl.BillDAOImpl;

import java.io.IOException;

@WebServlet({"/admin/orders", "/admin/orders/update"})
public class OrderManagementServlet extends HttpServlet {
    private BillDAO billDAO = new BillDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // Xử lý cập nhật trạng thái đơn hàng
        if (uri.contains("update")) {
            Long id = Long.parseLong(req.getParameter("id"));
            String status = req.getParameter("status");

            Bill bill = billDAO.findById(id);
            if (bill != null) {
                bill.setStatus(status);
                billDAO.update(bill);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }

        // Hiển thị danh sách hóa đơn
        req.setAttribute("orders", billDAO.findAll());
        req.setAttribute("view", "/views/admin/order-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}