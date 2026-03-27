package poly.cafe.dao.impl;

import poly.cafe.dao.CategoryDAO;
import poly.cafe.entity.Category;

public class CategoryDAOImpl
        extends GenericDAOImpl<Category, Long>
        implements CategoryDAO {

    public CategoryDAOImpl() {
        super(Category.class);
    }

}