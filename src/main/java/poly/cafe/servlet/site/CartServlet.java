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
    private DrinkService drinkService = new DrinkServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if (uri.contains("add")) {
            Long id = Long.parseLong(req.getParameter("id"));
            if (cart.containsKey(id)) {
                CartItem item = cart.get(id);
                item.setQuantity(item.getQuantity() + 1);
            } else {
                Drink d = drinkService.findById(id);
                cart.put(id, new CartItem(d.getId(), d.getName(), d.getPrice(), 1, d.getImage()));
            }
            resp.sendRedirect(req.getContextPath() + "/drinks"); // Thêm xong quay lại menu
            return;
        }

        if (uri.contains("remove")) {
            Long id = Long.parseLong(req.getParameter("id"));
            cart.remove(id);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if (path.contains("/cart/add")) {
            // ... code thêm vào giỏ ...
            resp.sendRedirect(req.getContextPath() + "/drinks?message=added");
            return;
        } else if (path.contains("/cart/remove")) {
            // ... code xóa món ...
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        // Hiển thị trang giỏ hàng
        req.setAttribute("view", "/views/site/cart.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}