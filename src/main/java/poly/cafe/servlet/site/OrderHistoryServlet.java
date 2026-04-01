package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Bill;
import poly.cafe.entity.User;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class OrderHistoryServlet extends HttpServlet {

    private final BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Bill> myOrders = billService.findByUser(user.getId());
        req.setAttribute("orders", myOrders);
        req.setAttribute("view", "/views/site/order-history.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}