package poly.cafe.service;

import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.model.CartItem;

import java.util.List;
import java.util.Map;

public interface BillService {
    void checkout(Bill bill, Map<Long, CartItem> cart);
    List<Bill> findByUser(Long userId);
    void createWithDetail(Bill bill, BillDetail detail);
    void updateStatus(Long id, String status);
    boolean cancelOrder(Long billId, Long userId); // Thêm userId để bảo mật
    List<Bill> findAll(); // Lấy tất cả đơn hàng cho nhân viên/admin
}