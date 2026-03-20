package poly.cafe.dao;

import java.util.List;

public interface GenericDAO<T, ID> {

    void create(T entity);

    void update(T entity);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}