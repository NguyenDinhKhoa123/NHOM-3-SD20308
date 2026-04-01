package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.BillDetailDAO;
import poly.cafe.entity.BillDetail;
import poly.cafe.utils.JPAUtil;

import java.util.List;

public class BillDetailDAOImpl extends GenericDAOImpl<BillDetail, Long> implements BillDetailDAO {

    public BillDetailDAOImpl() {
        super(BillDetail.class);
    }

    @Override
    public List<BillDetail> findByBillId(Long billId) { // Đổi tên ở đây
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Nên dùng JOIN FETCH để lấy luôn thông tin Drink trong 1 lần query, tránh lỗi LazyInitialization
            String jpql = "SELECT bd FROM BillDetail bd JOIN FETCH bd.drink WHERE bd.bill.id = :bid";
            TypedQuery<BillDetail> query = em.createQuery(jpql, BillDetail.class);
            query.setParameter("bid", billId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}