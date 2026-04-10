package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.cafe.entity.Drink;
import poly.cafe.model.CartItem;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.DrinkServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/cart", "/cart/add", "/cart/remove", "/cart/update"})
public class CartServlet extends HttpServlet {

    private final DrinkService drinkService = new DrinkServiceImpl();

    // doGet dùng để HIỂN THỊ giỏ hàng và XÓA món ăn (vì nút Xóa là thẻ <a>)
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String path = req.getServletPath();

        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // Xóa món khỏi giỏ
        if (path.contains("/remove")) {
            Long id = Long.parseLong(req.getParameter("id"));
            cart.remove(id);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Mặc định: Hiển thị trang giỏ hàng
        req.setAttribute("view", "/views/site/cart.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    // doPost dùng để NHẬN FORM (Thêm vào giỏ, Mua ngay, Cập nhật số lượng)
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String path = req.getServletPath();

        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        try {
            // Xử lý Form từ trang Thực Đơn (Thêm món)
            if (path.contains("/add")) {
                Long id = Long.parseLong(req.getParameter("id"));
                int qty = Integer.parseInt(req.getParameter("quantity")); // Lấy số lượng người dùng nhập
                String action = req.getParameter("action"); // Lấy giá trị nút bấm ("add" hoặc "buy")

                if (cart.containsKey(id)) {
                    // Nếu có rồi thì cộng dồn số lượng
                    cart.get(id).setQuantity(cart.get(id).getQuantity() + qty);
                } else {
                    // Chưa có thì tạo mới
                    Drink d = drinkService.findById(id);
                    if (d != null && d.getActive()) {
                        cart.put(id, new CartItem(d.getId(), d.getName(), d.getPrice(), qty, d.getImage()));
                    }
                }

                // Chuyển hướng thông minh dựa vào nút khách bấm
                if ("buy".equals(action)) {
                    resp.sendRedirect(req.getContextPath() + "/cart"); // Bấm "Mua ngay" -> Bay qua Giỏ hàng
                } else {
                    resp.sendRedirect(req.getContextPath() + "/drinks"); // Bấm "Thêm vào giỏ" -> Ở lại mua tiếp
                }
                return;
            }

            // Xử lý Form từ trang Giỏ Hàng (Sửa số lượng)
            if (path.contains("/update")) {
                Long id = Long.parseLong(req.getParameter("id"));
                int qty = Integer.parseInt(req.getParameter("quantity"));

                if (cart.containsKey(id)) {
                    if (qty > 0) {
                        cart.get(id).setQuantity(qty);
                    } else {
                        cart.remove(id); // Nếu nhập số 0 thì tự xóa luôn
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}