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
    @Override
    public List<Drink> searchByName(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Drink d WHERE LOWER(d.name) LIKE LOWER(:keyword)";
            TypedQuery<Drink> query = em.createQuery(jpql, Drink.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

        @Override
        public List<Drink> searchDrinks(String name, Long categoryId, Boolean active, int page, int pageSize) {
            EntityManager em = JPAUtil.getEntityManager();
            try {
                StringBuilder jpql = new StringBuilder("SELECT d FROM Drink d WHERE 1=1");
                if (name != null && !name.trim().isEmpty()) jpql.append(" AND d.name LIKE :name");
                if (categoryId != null) jpql.append(" AND d.category.id = :cateId");
                if (active != null) jpql.append(" AND d.active = :active");

                TypedQuery<Drink> query = em.createQuery(jpql.toString(), Drink.class);
                if (name != null && !name.trim().isEmpty()) query.setParameter("name", "%" + name + "%");
                if (categoryId != null) query.setParameter("cateId", categoryId);
                if (active != null) query.setParameter("active", active);

                query.setFirstResult((page - 1) * pageSize);
                query.setMaxResults(pageSize);
                return query.getResultList();
            } finally { em.close(); }
        }

        @Override
        public long countSearch(String name, Long categoryId, Boolean active) {
            EntityManager em = JPAUtil.getEntityManager();
            try {
                StringBuilder jpql = new StringBuilder("SELECT COUNT(d) FROM Drink d WHERE 1=1");
                if (name != null && !name.trim().isEmpty()) jpql.append(" AND d.name LIKE :name");
                if (categoryId != null) jpql.append(" AND d.category.id = :cateId");
                if (active != null) jpql.append(" AND d.active = :active");

                TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
                if (name != null && !name.trim().isEmpty()) query.setParameter("name", "%" + name + "%");
                if (categoryId != null) query.setParameter("cateId", categoryId);
                if (active != null) query.setParameter("active", active);
                return query.getSingleResult();
            } finally { em.close(); }
        }
    }

