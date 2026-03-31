package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.BillDAO;
import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
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
            // Thêm điều kiện: status không nằm trong danh sách bị hủy
            // Mình dùng NOT IN để "né" cả 1 chữ L và 2 chữ L cho chắc chắn
            String jpql = "SELECT b FROM Bill b WHERE b.user.id = :uid " +
                    "AND b.status NOT IN ('cancelled') " +
                    "ORDER BY b.createDate DESC";

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

    @Override
    public void createWithDetail(Bill bill, BillDetail detail) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bill);       // Lưu Bill trước để có ID
            em.persist(detail);     // Lưu Detail sau
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
            em.getTransaction().begin(); // (1) Mở cổng giao dịch

            // Sử dụng merge để JPA nhận diện đối tượng và cập nhật theo ID
            em.merge(bill);

            em.getTransaction().commit(); // (2) CHỐT HẠ: Ghi dữ liệu xuống DB
            System.out.println("==> Log: Đã Commit thành công đơn hàng " + bill.getCode());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Nếu lỗi thì hủy để tránh rác dữ liệu
            }
            e.printStackTrace();
        } finally {
            em.close(); // Đóng kết nối
        }
    }
    }
