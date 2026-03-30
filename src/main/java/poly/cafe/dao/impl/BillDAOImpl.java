package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.BillDAO;
import poly.cafe.entity.Bill;
import poly.cafe.utils.JPAUtil;
import java.util.List;

public class BillDAOImpl extends GenericDAOImpl<Bill, Long> implements BillDAO {

    public BillDAOImpl() {
        super(Bill.class);
    }

    @Override
    public List<Bill> findByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT b FROM Bill b WHERE b.user.id = :uid ORDER BY b.createDate DESC";
            TypedQuery<Bill> query = em.createQuery(jpql, Bill.class);
            query.setParameter("uid", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Bill findByCode(String code) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT b FROM Bill b WHERE b.code = :code";
            TypedQuery<Bill> query = em.createQuery(jpql, Bill.class);
            query.setParameter("code", code);
            List<Bill> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }
    @Override
    public double getTotalRevenue() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT SUM(b.total) FROM Bill b WHERE b.status = 'paid'";
            Double result = em.createQuery(jpql, Double.class).getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public long countTotalOrders() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(b) FROM Bill b";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}