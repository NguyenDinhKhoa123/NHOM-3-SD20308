package poly.cafe.dao;

import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;

import java.util.List;

public interface BillDAO extends GenericDAO<Bill, Long> {

    // Tìm các hóa đơn của một khách hàng cụ thể (để làm lịch sử mua hàng)
    List<Bill> findByUser(Long userId);
    void update(Bill bill);
    // Tìm hóa đơn theo mã code (Ví dụ: BILL-ABCD)
    Bill findByCode(String code);
    void createWithDetail(Bill bill, BillDetail detail);
    double getTotalRevenue();
    long countTotalOrders();
}