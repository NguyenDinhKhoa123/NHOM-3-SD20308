package poly.cafe.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.dao.CategoryDAO;
import poly.cafe.dao.impl.CategoryDAOImpl;
import poly.cafe.entity.Category;
import poly.cafe.utils.ParamUtil;

@WebServlet({
        "/category/index",
        "/category/edit/*",
        "/category/create",
        "/category/update",
        "/category/delete/*"
})
public class CategoryServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // Thay đổi tên gọi hàm ở đây
        if (uri.contains("edit")) {
            this.editCategory(req, resp);
        } else if (uri.contains("create")) {
            this.createCategory(req, resp);
        } else if (uri.contains("update")) {
            this.updateCategory(req, resp);
        } else if (uri.contains("delete")) {
            this.deleteCategory(req, resp);
        }

        // Luôn quay về hiển thị danh sách
        this.listCategory(req, resp);
    }

    private void listCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> list = dao.findAll();
        req.setAttribute("items", list);
        req.getRequestDispatcher("/category.jsp").forward(req, resp);
    }

    private void editCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String pathInfo = req.getRequestURI();
            Long id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf("/") + 1));
            Category cat = dao.findById(id);
            req.setAttribute("item", cat);
        } catch (Exception e) {
            req.setAttribute("error", "Không tìm thấy ID!");
        }
    }

    private void createCategory(HttpServletRequest req, HttpServletResponse resp) {
        Category cat = new Category();
        cat.setName(ParamUtil.getString(req, "name", ""));
        cat.setActive(ParamUtil.getBoolean(req, "active", false));
        dao.create(cat);
        req.setAttribute("message", "Thêm mới thành công!");
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) {
        Long id = (long) ParamUtil.getInt(req, "id", 0);
        Category cat = dao.findById(id);
        if (cat != null) {
            cat.setName(ParamUtil.getString(req, "name", ""));
            cat.setActive(ParamUtil.getBoolean(req, "active", false));
            dao.update(cat);
            req.setAttribute("message", "Cập nhật thành công!");
        }
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String pathInfo = req.getRequestURI();
            Long id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf("/") + 1));
            dao.delete(id);
            req.setAttribute("message", "Xóa thành công!");
        } catch (Exception e) {
            req.setAttribute("error", "Xóa thất bại!");
        }
    }
}