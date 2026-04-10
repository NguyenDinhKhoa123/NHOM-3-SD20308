package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.BillDAO;
import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.User;
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
            String jpql = "SELECT b FROM Bill b WHERE b.user.id = :uid " +
                    "AND b.status NOT IN ('cancelled') " +
                    "ORDER BY b.createDate DESC";
            return em.createQuery(jpql, Bill.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Bill findByCode(String code) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Bill> list = em.createQuery(
                            "SELECT b FROM Bill b WHERE b.code = :code", Bill.class)
                    .setParameter("code", code)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public double getTotalRevenue() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Double result = em.createQuery(
                            "SELECT SUM(b.total) FROM Bill b WHERE b.status = 'paid'", Double.class)
                    .getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public long countTotalOrders() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(b) FROM Bill b", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void createWithDetail(Bill bill, BillDetail detail) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bill);
            em.persist(detail);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Bill bill) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bill);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Bill> findAllPaginated(int page, int pageSize) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Bill b ORDER BY b.createDate DESC", Bill.class)
                    .setFirstResult((page - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(b) FROM Bill b", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Bill> findBillsForManagement(User currentUser, List<String> statuses, int page, int pageSize) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            boolean isStaff = "staff".equalsIgnoreCase(currentUser.getRole());
            String jpql = "SELECT b FROM Bill b WHERE b.status IN :statuses"
                    + (isStaff ? " AND b.user.id = :uid" : "")
                    + " ORDER BY b.createDate DESC";

            TypedQuery<Bill> query = em.createQuery(jpql, Bill.class)
                    .setParameter("statuses", statuses)
                    .setFirstResult((page - 1) * pageSize)
                    .setMaxResults(pageSize);

            if (isStaff) query.setParameter("uid", currentUser.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countBillsForManagement(User currentUser, List<String> statuses) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            boolean isStaff = "staff".equalsIgnoreCase(currentUser.getRole());
            String jpql = "SELECT COUNT(b) FROM Bill b WHERE b.status IN :statuses"
                    + (isStaff ? " AND b.user.id = :uid" : "");

            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("statuses", statuses);

            if (isStaff) query.setParameter("uid", currentUser.getId());
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}