package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.Drink;
import poly.cafe.entity.User;
import poly.cafe.service.BillService;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.BillServiceImpl;
import poly.cafe.service.impl.DrinkServiceImpl;
import poly.cafe.utils.AuthUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    private final DrinkService drinkService = new DrinkServiceImpl();
    private final BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=Vui lòng đăng nhập để mua hàng");
            return;
        }

        try {
            Long drinkId = Long.parseLong(req.getParameter("id"));
            Drink drink = drinkService.findById(drinkId);

            Bill bill = new Bill();
            bill.setCode("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            bill.setCreateDate(LocalDateTime.now());
            bill.setTotal(drink.getPrice());
            bill.setStatus("pending");
            bill.setUser(user);

            BillDetail detail = new BillDetail();
            detail.setDrink(drink);
            detail.setPrice(drink.getPrice());
            detail.setQuantity(1);
            detail.setBill(bill);

            billService.createWithDetail(bill, detail);
            resp.sendRedirect(req.getContextPath() + "/my-orders?message=success");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/drinks?error=failed");
        }
    }
}