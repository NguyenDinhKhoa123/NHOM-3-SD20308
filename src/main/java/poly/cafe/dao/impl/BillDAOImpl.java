package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.BillDAO;
import poly.cafe.entity.Bill;
import poly.cafe.utils.JPAUtil;

public class BillDAOImpl extends GenericDAOImpl<Bill, Long> implements BillDAO {

    public BillDAOImpl() {
        super(Bill.class);
    }

    @Override
    public Bill findByCard(Long cardId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT b FROM Bill b WHERE b.card.id = :cid AND b.status = 'pending'";
            TypedQuery<Bill> query = em.createQuery(jpql, Bill.class);
            query.setParameter("cid", cardId);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateStatus(Long billId, String status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Bill bill = em.find(Bill.class, billId);
            if (bill != null) {
                bill.setStatus(status);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}