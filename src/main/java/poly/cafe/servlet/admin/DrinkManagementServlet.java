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

@MultipartConfig // Bắt buộc phải có để upload ảnh
@WebServlet({
        "/admin/drinks",
        "/admin/drinks/edit",
        "/admin/drinks/create",
        "/admin/drinks/update",
        "/admin/drinks/delete"
})
public class DrinkManagementServlet extends HttpServlet {
    private DrinkService drinkService = new DrinkServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            // 1. XỬ LÝ NÚT "SỬA" (Đổ dữ liệu lên Form)
            if (path.contains("/edit")) {
                Long id = Long.parseLong(req.getParameter("id"));
                Drink drink = drinkService.findById(id);
                req.setAttribute("drinkForm", drink);
            }

            // 2. XỬ LÝ NÚT "XÓA"
            else if (path.contains("/delete")) {
                Long id = Long.parseLong(req.getParameter("id"));
                drinkService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/drinks");
                return;
            }

            // 3. XỬ LÝ TÌM KIẾM (CHỈ TÊN & LOẠI)
            String searchName = req.getParameter("searchName");
            String searchCateIdStr = req.getParameter("searchCategoryId");
            Long searchCateId = (searchCateIdStr == null || searchCateIdStr.isEmpty()) ? null : Long.parseLong(searchCateIdStr);

            // Gọi hàm search: Truyền null cho 'active' để Admin thấy tất cả món
            List<Drink> list = drinkService.search(searchName, searchCateId, null, 1, 100);

            // 4. GỬI DỮ LIỆU RA JSP
            req.setAttribute("drinks", list);
            req.setAttribute("categories", categoryService.findAll()); // Để nạp vào dropdown loại

            // Giữ lại giá trị tìm kiếm trên ô nhập
            req.setAttribute("searchName", searchName);
            req.setAttribute("searchCateId", searchCateId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("view", "/views/admin/drink-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Fix lỗi tiếng Việt
        try {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            Double price = Double.parseDouble(req.getParameter("price"));
            Long categoryId = Long.parseLong(req.getParameter("categoryId"));
            String desc = req.getParameter("description");
            boolean active = req.getParameter("active") != null; // Checkbox trạng thái

            Drink drink;
            if (idStr == null || idStr.isEmpty()) {
                // TẠO MỚI
                drink = new Drink();
                String fileName = FileUtil.save(req, "image", "uploads/drinks");
                drink.setImage(fileName != null ? fileName : "default.jpg");
            } else {
                // CẬP NHẬT
                drink = drinkService.findById(Long.parseLong(idStr));
                String fileName = FileUtil.save(req, "image", "uploads/drinks");
                if (fileName != null) {
                    drink.setImage(fileName); // Chỉ thay ảnh nếu người dùng chọn file mới
                }
            }

            drink.setName(name);
            drink.setPrice(price);
            drink.setDescription(desc);
            drink.setActive(active);

            Category cat = new Category();
            cat.setId(categoryId);
            drink.setCategory(cat);

            if (idStr == null || idStr.isEmpty()) {
                drinkService.create(drink);
            } else {
                drinkService.update(drink);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/drinks");
    }
}