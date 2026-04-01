package poly.cafe.service.impl;

import poly.cafe.dao.DrinkDAO;
import poly.cafe.dao.impl.DrinkDAOImpl;
import poly.cafe.entity.Drink;
import poly.cafe.service.DrinkService;
import java.util.List;

public class DrinkServiceImpl implements DrinkService {
    private DrinkDAO dao = new DrinkDAOImpl();

    @Override
    public List<Drink> findAll() {
        return dao.findAll();
    }

    @Override
    public Drink findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void create(Drink drink) {
        dao.create(drink);
    }

    @Override
    public void update(Drink drink) {
        dao.update(drink);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    // TRIỂN KHAI TÌM KIẾM & PHÂN TRANG
    @Override
    public List<Drink> search(String name, Long categoryId, Boolean active, int page, int pageSize) {
        // Chuyển trực tiếp dữ liệu xuống DAO để query SQL
        return dao.searchDrinks(name, categoryId, active, page, pageSize);
    }

    @Override
    public int countPages(String name, Long categoryId, Boolean active, int pageSize) {
        // 1. Hỏi DAO xem tổng cộng có bao nhiêu món thỏa mãn điều kiện lọc
        long totalRecords = dao.countSearch(name, categoryId, active);

        // 2. Tính toán số trang (Ví dụ: 21 món / 10 món mỗi trang = 2.1 -> làm tròn thành 3 trang)
        return (int) Math.ceil((double) totalRecords / pageSize);
    }
}