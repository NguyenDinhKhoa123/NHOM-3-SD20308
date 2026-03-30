package poly.cafe.service;

import poly.cafe.entity.Drink;

import java.util.List;

public interface DrinkService {
    List<Drink> findAll();

    List<Drink> search(String keyword);

    void create(Drink drink);

    Drink findById(Long id);

    void delete(Long id);
}
