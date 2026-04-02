package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poly.cafe.entity.Bill;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;

import java.io.IOException;

@WebServlet({"/admin/orders", "/admin/orders/detail", "/admin/orders/cancel", "/admin/orders/update"})
public class OrderManagementServlet extends HttpServlet {

    private BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            // 1. HỦY ĐƠN
            if (path.contains("/cancel")) {
                Long id = Long.parseLong(req.getParameter("id"));
                Bill bill = billService.findById(id);

                if (bill != null && "pending".equals(bill.getStatus())) {
                    bill.setStatus("cancelled");
                    billService.update(bill);
                }

                resp.sendRedirect(req.getContextPath() + "/admin/orders");
                return;
            }

            // 2. UPDATE STATUS
            if (path.contains("/update")) {
                Long id = Long.parseLong(req.getParameter("id"));
                String status = req.getParameter("status");

                billService.updateStatus(id, status);

                resp.sendRedirect(req.getContextPath() + "/admin/orders");
                return;
            }

            // 3. DETAIL
            if (path.contains("/detail")) {
                Long id = Long.parseLong(req.getParameter("id"));

                req.setAttribute("bill", billService.findById(id));
                req.setAttribute("details", billService.findDetailsByBillId(id));
                req.setAttribute("view", "/views/admin/order-detail.jsp");

                req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
                return;
            }

            boolean isHistory = "true".equals(req.getParameter("history"));
            req.setAttribute("isHistory", isHistory);

            String pageStr = req.getParameter("page");
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
            int pageSize = 10;

            req.setAttribute("orders",
                    billService.getBillsByStatus(currentPage, pageSize, isHistory)
            );

            req.setAttribute("currentPage", currentPage);

            req.setAttribute("totalPages",
                    billService.countBillsByStatus(pageSize, isHistory)
            );

            req.setAttribute("view", "/views/admin/order-list.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}