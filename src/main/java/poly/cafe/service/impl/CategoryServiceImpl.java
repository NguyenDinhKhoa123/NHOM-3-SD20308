package poly.cafe.service.impl;

import poly.cafe.dao.CategoryDAO;
import poly.cafe.dao.impl.CategoryDAOImpl;
import poly.cafe.entity.Category;
import poly.cafe.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    // Tiêm DAO vào để gọi tầng dữ liệu
    private CategoryDAO dao = new CategoryDAOImpl();

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void create(Category category) {
        dao.create(category);
    }

    @Override
    public void update(Category category) {
        dao.update(category);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}