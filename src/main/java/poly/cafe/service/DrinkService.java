package poly.cafe.service;

import poly.cafe.entity.Drink;
import java.util.List;

public interface DrinkService {
    List<Drink> findAll();
    Drink findById(Long id);
    void create(Drink drink);
    void update(Drink drink);
    void delete(Long id);

    // ĐỔI String status -> Boolean active ĐỂ ĐỒNG BỘ
    List<Drink> search(String name, Long categoryId, Boolean active, int page, int pageSize);

    int countPages(String name, Long categoryId, Boolean active, int pageSize);
}