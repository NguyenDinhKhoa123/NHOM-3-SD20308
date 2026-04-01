package poly.cafe.dao;

import poly.cafe.entity.Drink;
import java.util.List;

public interface DrinkDAO extends GenericDAO<Drink, Long> {

    List<Drink> findByCategory(Long categoryId);

    List<Drink> searchByName(String keyword);


    List<Drink> searchDrinks(String name, Long categoryId, Boolean active, int page, int pageSize);

    long countSearch(String name, Long categoryId, Boolean active);
}