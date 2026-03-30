package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.dao.BillDAO;
import poly.cafe.dao.impl.BillDAOImpl;
import poly.cafe.entity.Bill;
import poly.cafe.entity.User;
import poly.cafe.utils.AuthUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class OrderHistoryServlet extends HttpServlet {
    private BillDAO billDAO = new BillDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy user từ session
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Lấy danh sách hóa đơn của user này (Sử dụng hàm findByUser vừa viết)
        List<Bill> myOrders = billDAO.findByUser(user.getId());

        // 3. Đưa ra giao diện
        req.setAttribute("orders", myOrders);
        req.setAttribute("view", "/views/site/order-history.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}