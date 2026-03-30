package poly.cafe.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    T create(T entity); // Đảm bảo trả về T thay vì void
    void update(T entity);
    void delete(ID id);
    T findById(ID id);
    List<T> findAll();
}