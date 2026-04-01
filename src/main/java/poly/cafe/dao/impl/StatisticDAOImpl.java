package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import poly.cafe.utils.JPAUtil;
import java.util.List;

public class StatisticDAOImpl {

    public List<Object[]> getTop5Drinks(String start, String end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String sql = "SELECT TOP 5 d.name, SUM(bd.quantity) FROM bill_details bd " +
                    "JOIN bills b ON bd.bill_id = b.id " +
                    "JOIN drinks d ON bd.drink_id = d.id " +
                    "WHERE b.status = 'paid' AND b.create_date BETWEEN ?1 AND ?2 " +
                    "GROUP BY d.name ORDER BY SUM(bd.quantity) DESC";
            return em.createNativeQuery(sql)
                    .setParameter(1, start)
                    .setParameter(2, end)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> getRevenueByDate(String start, String end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String sql = "SELECT CAST(create_date AS DATE), SUM(total) FROM bills " +
                    "WHERE status = 'paid' AND create_date BETWEEN ?1 AND ?2 " +
                    "GROUP BY CAST(create_date AS DATE) ORDER BY 1";
            return em.createNativeQuery(sql)
                    .setParameter(1, start)
                    .setParameter(2, end)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}