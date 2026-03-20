package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.DrinkDAO;
import poly.cafe.entity.Drink;
import poly.cafe.utils.JPAUtil;

import java.util.List;

public class DrinkDAOImpl extends GenericDAOImpl<Drink, Long> implements DrinkDAO {

    public DrinkDAOImpl() {
        super(Drink.class);
    }

    @Override
    public List<Drink> findByCategory(Long categoryId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Drink d WHERE d.category.id = :cid";
            TypedQuery<Drink> query = em.createQuery(jpql, Drink.class);
            query.setParameter("cid", categoryId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}