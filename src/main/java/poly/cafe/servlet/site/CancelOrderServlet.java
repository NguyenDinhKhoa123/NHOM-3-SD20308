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

    private final BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = AuthUtil.getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                boolean isSuccess = billService.cancelOrder(Long.parseLong(idStr), user.getId());
                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/my-orders?message=canceled_success");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/my-orders?error=cannot_cancel");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/my-orders");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/my-orders?error=system_error");
        }
    }
}