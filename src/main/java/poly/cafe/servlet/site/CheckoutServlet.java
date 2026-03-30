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
    private BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = AuthUtil.getUser(req);

        // 1. Kiểm tra đăng nhập
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=vui-long-dang-nhap");
            return;
        }

        // 2. Lấy giỏ hàng
        Map<Long, CartItem> cart = (Map<Long, CartItem>) req.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/drinks");
            return;
        }

        // 3. Tính tổng tiền
        double total = 0;
        for (CartItem item : cart.values()) {
            total += item.getPrice() * item.getQuantity();
        }

        // 4. Tạo đối tượng Bill
        Bill bill = new Bill();
        bill.setCode("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        bill.setCreateDate(LocalDateTime.now());
        bill.setUser(user);
        bill.setTotal(total);
        bill.setStatus("pending");

        // 5. Gọi Service lưu vào DB
        billService.checkout(bill, cart);

        // 6. Xóa giỏ hàng sau khi đặt thành công
        req.getSession().removeAttribute("cart");

        req.setAttribute("message", "Đặt hàng thành công! Mã đơn hàng: " + bill.getCode());
        req.setAttribute("view", "/views/site/home.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}