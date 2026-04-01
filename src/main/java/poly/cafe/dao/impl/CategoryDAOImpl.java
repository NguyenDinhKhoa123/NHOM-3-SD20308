package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import poly.cafe.dao.CategoryDAO;
import poly.cafe.entity.Category;
import poly.cafe.utils.JPAUtil;

import java.util.List;

public class CategoryDAOImpl
        extends GenericDAOImpl<Category, Long>
        implements CategoryDAO {

    public CategoryDAOImpl() {
        super(Category.class);
    }

}