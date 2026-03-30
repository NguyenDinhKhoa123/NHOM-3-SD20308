package poly.cafe.dao;

import poly.cafe.entity.Bill;
import java.util.List;

public interface BillDAO extends GenericDAO<Bill, Long> {

    // Tìm các hóa đơn của một khách hàng cụ thể (để làm lịch sử mua hàng)
    List<Bill> findByUser(Long userId);

    // Tìm hóa đơn theo mã code (Ví dụ: BILL-ABCD)
    Bill findByCode(String code);

    double getTotalRevenue();
    long countTotalOrders();
}