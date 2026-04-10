package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Bill;
import poly.cafe.entity.User;
import poly.cafe.service.BillService;
import poly.cafe.service.impl.BillServiceImpl;
import java.io.IOException;

@WebServlet({"/admin/orders", "/admin/orders/detail", "/admin/orders/cancel", "/admin/orders/update"})
public class OrderManagementServlet extends HttpServlet {

    private final BillService billService = new BillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        try {
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

            if (path.contains("/update")) {
                billService.updateStatus(
                        Long.parseLong(req.getParameter("id")),
                        req.getParameter("status")
                );
                resp.sendRedirect(req.getContextPath() + "/admin/orders");
                return;
            }

            if (path.contains("/detail")) {
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("bill",    billService.findById(id));
                req.setAttribute("details", billService.findDetailsByBillId(id));
                req.setAttribute("view",    "/views/admin/order-detail.jsp");
                req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
                return;
            }

            boolean isHistory = "true".equals(req.getParameter("history"));
            int currentPage   = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
            int pageSize      = 10;
            User currentUser  = (User) req.getSession().getAttribute("user");

            req.setAttribute("orders",      billService.getBillsForManagement(currentUser, currentPage, pageSize, isHistory));
            req.setAttribute("totalPages",  billService.countBillsForManagement(currentUser, pageSize, isHistory));
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("isHistory",   isHistory);
            req.setAttribute("view",        "/views/admin/order-list.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}