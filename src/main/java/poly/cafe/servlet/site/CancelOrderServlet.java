package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.User;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;
import poly.cafe.utils.AuthUtil;

import java.io.IOException;

@WebServlet("/my-orders/cancel")
public class CancelOrderServlet extends HttpServlet {
    private BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy user từ session
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                Long orderId = Long.parseLong(idStr);

                // 2. Gọi Service và nhận kết quả THỰC TẾ từ DB
                boolean isSuccess = billService.cancelOrder(orderId, user.getId());

                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/my-orders?message=canceled_success");
                } else {
                    // Nếu Service trả về false (do sai trạng thái hoặc sai người dùng)
                    resp.sendRedirect(req.getContextPath() + "/my-orders?error=cannot_cancel");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/my-orders");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi ra Console để kiểm tra nếu sập
            resp.sendRedirect(req.getContextPath() + "/my-orders?error=system_error");
        }
    }
}