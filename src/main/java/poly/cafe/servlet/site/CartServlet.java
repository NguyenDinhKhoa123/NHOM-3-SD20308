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

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();

        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if (uri.contains("add")) {
            Long id = Long.parseLong(req.getParameter("id"));
            if (cart.containsKey(id)) {
                cart.get(id).setQuantity(cart.get(id).getQuantity() + 1);
            } else {
                Drink d = drinkService.findById(id);
                cart.put(id, new CartItem(d.getId(), d.getName(), d.getPrice(), 1, d.getImage()));
            }
            resp.sendRedirect(req.getContextPath() + "/drinks");
            return;
        }

        if (uri.contains("remove")) {
            cart.remove(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("view", "/views/site/cart.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}