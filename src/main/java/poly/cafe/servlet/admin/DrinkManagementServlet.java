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

@MultipartConfig
@WebServlet({"/admin/drinks", "/admin/drinks/create", "/admin/drinks/delete"})
public class DrinkManagementServlet extends HttpServlet {
    private DrinkService drinkService = new DrinkServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                drinkService.delete(id);
            } catch (Exception e) {
                // Có thể thêm báo lỗi nếu không xóa được
            }
            resp.sendRedirect(req.getContextPath() + "/admin/drinks");
            return;
        }

        // Đổ dữ liệu ra trang quản lý
        req.setAttribute("categories", categoryService.findAll());
        req.setAttribute("drinks", drinkService.findAll());
        req.setAttribute("view", "/views/admin/drink-management.jsp");
        req.getRequestDispatcher("/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. Đọc thông tin từ Form
            String name = req.getParameter("name");
            String priceStr = req.getParameter("price");
            String categoryIdStr = req.getParameter("categoryId");
            String desc = req.getParameter("description");

            // --- BẮT ĐẦU VALIDATION ---
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("error", "Tên món không được để trống!");
                doGet(req, resp);
                return;
            }

            double price = 0;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    req.setAttribute("error", "Giá tiền phải lớn hơn 0!");
                    doGet(req, resp);
                    return;
                }
            } catch (Exception e) {
                req.setAttribute("error", "Giá tiền phải là số hợp lệ!");
                doGet(req, resp);
                return;
            }
            // --- KẾT THÚC VALIDATION ---

            // 2. Xử lý Upload ảnh
            String fileName = FileUtil.save(req, "image", "uploads/drinks");
            if (fileName == null) {
                // Bạn có thể để ảnh mặc định nếu khách không chọn ảnh
                fileName = "default.jpg";
            }

            // 3. Tạo đối tượng Entity và set giá trị
            Drink drink = new Drink();
            drink.setName(name);
            drink.setPrice(price);
            drink.setDescription(desc);
            drink.setImage(fileName);
            drink.setActive(true);

            Category cat = new Category();
            cat.setId(Long.parseLong(categoryIdStr));
            drink.setCategory(cat);

            // 4. Lưu vào DB
            drinkService.create(drink);

            // Thành công thì quay về danh sách
            resp.sendRedirect(req.getContextPath() + "/admin/drinks");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            doGet(req, resp);
        }
    }
}