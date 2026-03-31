package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.*;
import poly.cafe.service.*;
import poly.cafe.service.impl.*;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {
    private DrinkService drinkService = new DrinkServiceImpl();
    private BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Kiểm tra đăng nhập
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=Vui lòng đăng nhập để mua hàng");
            return;
        }

        try {
            // 2. Lấy thông tin món đồ uống
            Long drinkId = Long.parseLong(req.getParameter("id"));
            Drink drink = drinkService.findById(drinkId);

            // 3. Tạo Hóa đơn (Bill)
            Bill bill = new Bill();
            bill.setCode("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            bill.setCreateDate(LocalDateTime.now());
            bill.setTotal(drink.getPrice());
            bill.setStatus("pending"); // Chờ xác nhận
            bill.setUser(user);

            // 4. Tạo chi tiết hóa đơn (BillDetail)
            BillDetail detail = new BillDetail();
            detail.setDrink(drink);
            detail.setPrice(drink.getPrice());
            detail.setQuantity(1);
            detail.setBill(bill);

            // 5. Lưu vào DB (Service của bạn cần có hàm save Bill)
            billService.createWithDetail(bill, detail);

            // 6. Chuyển hướng về trang lịch sử mua hàng
            resp.sendRedirect(req.getContextPath() + "/my-orders?message=success");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/drinks?error=failed");
        }
    }
}