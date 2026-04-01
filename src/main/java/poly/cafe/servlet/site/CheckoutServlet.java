package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Bill;
import poly.cafe.entity.User;
import poly.cafe.model.CartItem;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final BillService billService = new BillServiceImpl();

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=vui-long-dang-nhap");
            return;
        }

        Map<Long, CartItem> cart = (Map<Long, CartItem>) req.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/drinks");
            return;
        }

        double total = cart.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Bill bill = new Bill();
        bill.setCode("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        bill.setCreateDate(LocalDateTime.now());
        bill.setUser(user);
        bill.setTotal(total);
        bill.setStatus("pending");

        billService.checkout(bill, cart);
        req.getSession().removeAttribute("cart");

        req.setAttribute("message", "Đặt hàng thành công! Mã đơn hàng: " + bill.getCode());
        req.setAttribute("view", "/views/site/home.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}