package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;
import java.io.IOException;

@WebServlet({"/admin/orders", "/admin/orders/update", "/admin/sales-history"})
public class OrderManagementServlet extends HttpServlet {
    private BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // 1. XỬ LÝ CẬP NHẬT TRẠNG THÁI
        if (uri.contains("/update")) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                String status = req.getParameter("status");
                billService.updateStatus(id, status);

                // Sau khi update, quay lại trang quản lý
                resp.sendRedirect(req.getContextPath() + "/admin/orders?message=success");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2. PHÂN LOẠI HIỂN THỊ (Quản lý đơn vs Lịch sử bán hàng)
        if (uri.contains("/admin/sales-history")) {
            // Lịch sử bán hàng: Chỉ hiện đơn 'paid' hoặc 'canceled'
            // Bạn có thể viết thêm hàm findFinishedOrders() trong service,
            // hoặc đơn giản là dùng findAll() rồi lọc ở JSP
            req.setAttribute("orders", billService.findAll());
            req.setAttribute("isHistory", true); // Đánh dấu đây là trang lịch sử
        } else {
            // Quản lý đơn: Chỉ hiện đơn 'pending' hoặc 'confirmed'
            req.setAttribute("orders", billService.findAll());
            req.setAttribute("isHistory", false);
        }

        req.setAttribute("view", "/views/admin/order-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}