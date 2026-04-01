package poly.cafe.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.entity.Category;
import poly.cafe.entity.Drink;
import poly.cafe.service.CategoryService;
import poly.cafe.service.DrinkService;
import poly.cafe.service.impl.CategoryServiceImpl;
import poly.cafe.service.impl.DrinkServiceImpl;
import poly.cafe.utils.FileUtil;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet({
        "/admin/drinks",
        "/admin/drinks/edit",
        "/admin/drinks/create",
        "/admin/drinks/update",
        "/admin/drinks/delete"
})
public class DrinkManagementServlet extends HttpServlet {

    private final DrinkService drinkService = new DrinkServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            if (path.contains("/edit")) {
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("drinkForm", drinkService.findById(id));

            } else if (path.contains("/delete")) {
                drinkService.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/admin/drinks");
                return;
            }

            String searchName     = req.getParameter("searchName");
            String searchCateIdStr = req.getParameter("searchCategoryId");
            Long searchCateId = (searchCateIdStr == null || searchCateIdStr.isEmpty())
                    ? null : Long.parseLong(searchCateIdStr);

            List<Drink> list = drinkService.search(searchName, searchCateId, null, 1, 100);

            req.setAttribute("drinks",       list);
            req.setAttribute("categories",   categoryService.findAll());
            req.setAttribute("searchName",   searchName);
            req.setAttribute("searchCateId", searchCateId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("view", "/views/admin/drink-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String idStr     = req.getParameter("id");
            String name      = req.getParameter("name");
            Double price     = Double.parseDouble(req.getParameter("price"));
            Long categoryId  = Long.parseLong(req.getParameter("categoryId"));
            String desc      = req.getParameter("description");
            boolean active   = req.getParameter("active") != null;

            boolean isNew = (idStr == null || idStr.isEmpty());
            Drink drink = isNew ? new Drink() : drinkService.findById(Long.parseLong(idStr));

            String fileName = FileUtil.save(req, "image", "uploads/drinks");
            if (isNew) {
                drink.setImage(fileName != null ? fileName : "default.jpg");
            } else if (fileName != null) {
                drink.setImage(fileName);
            }

            drink.setName(name);
            drink.setPrice(price);
            drink.setDescription(desc);
            drink.setActive(active);

            Category cat = new Category();
            cat.setId(categoryId);
            drink.setCategory(cat);

            if (isNew) drinkService.create(drink);
            else       drinkService.update(drink);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/drinks");
    }
}