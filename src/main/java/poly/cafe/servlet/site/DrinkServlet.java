package poly.cafe.servlet.site;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Drink;
import poly.cafe.service.CategoryService;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.CategoryServiceImpl;
import poly.cafe.service.impl.DrinkServiceImpl;
import java.io.IOException;
import java.util.List;

@WebServlet("/drinks")
public class DrinkServlet extends HttpServlet {

    private final DrinkService drinkService = new DrinkServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name      = req.getParameter("name");
        String cateIdStr = req.getParameter("categoryId");
        String activeStr = req.getParameter("active");
        String pageStr   = req.getParameter("page");

        int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
        Long categoryId = (cateIdStr == null || cateIdStr.isEmpty()) ? null : Long.parseLong(cateIdStr);

        Boolean active = null;
        if ("true".equals(activeStr))       active = true;
        else if ("false".equals(activeStr)) active = false;

        List<Drink> list = drinkService.search(name, categoryId, active, currentPage, 10);
        int totalPages   = drinkService.countPages(name, categoryId, active, 10);

        req.setAttribute("drinks",          list);
        req.setAttribute("totalPages",      totalPages);
        req.setAttribute("currentPage",     currentPage);
        req.setAttribute("categories",      categoryService.findAll());
        req.setAttribute("name",            name);
        req.setAttribute("selectedCateId",  categoryId);
        req.setAttribute("selectedActive",  activeStr);
        req.setAttribute("view",            "/views/site/drink-list.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }
}