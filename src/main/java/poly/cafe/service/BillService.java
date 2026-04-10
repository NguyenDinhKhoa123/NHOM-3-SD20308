package poly.cafe.service;

import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.User;
import poly.cafe.model.CartItem;

import java.util.List;
import java.util.Map;

public interface BillService {
    void checkout(Bill bill, Map<Long, CartItem> cart);

    boolean cancelOrder(Long billId, Long userId);

    List<Bill> findByUser(Long userId);
    void updateStatus(Long id, String status);

    void createWithDetail(Bill bill, BillDetail detail);

    List<Bill> findAll();

    Bill findById(Long id);
    void update(Bill bill);
    List<BillDetail> findDetailsByBillId(Long billId);
    List<Bill> getBills(int page, int pageSize);
    int countBillPages(int pageSize);

    List<Bill> getBillsForManagement(poly.cafe.entity.User currentUser, int page, int pageSize, boolean isHistory);

    int countBillsForManagement(poly.cafe.entity.User currentUser, int pageSize, boolean isHistory);
}