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
    public List<Drink> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.findAll();
        }
        return dao.searchByName(keyword);
    }

    @Override
    public Drink findById(Long id) {
        // Gọi hàm findById đã có sẵn trong GenericDAOImpl
        return dao.findById(id);
    }

    @Override
    public void create(Drink drink) {
        dao.create(drink);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}