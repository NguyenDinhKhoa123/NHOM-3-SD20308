package poly.cafe.service.impl;

import poly.cafe.dao.BillDAO;
import poly.cafe.dao.BillDetailDAO;
import poly.cafe.dao.impl.BillDAOImpl;
import poly.cafe.dao.impl.BillDetailDAOImpl;
import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.Drink;
import poly.cafe.model.CartItem;
import poly.cafe.service.BillService;
import java.util.List;
import java.util.Map;

public class BillServiceImpl implements BillService {
    private BillDAO billDAO = new BillDAOImpl();
    private BillDetailDAO detailDAO = new BillDetailDAOImpl();
    @Override
    public void updateStatus(Long id, String status) {
        Bill bill = billDAO.findById(id);
        if (bill != null) {
            // Cho phép cập nhật thoải mái mọi trạng thái (pending -> confirmed -> paid)
            bill.setStatus(status.trim());
            billDAO.update(bill);
        }
    }
    @Override
    public boolean cancelOrder(Long billId, Long userId) {
        Bill bill = billDAO.findById(billId);
        if (bill != null && bill.getUser().getId().equals(userId)) {
            // Thay vì update status, ta gọi lệnh delete
            billDAO.delete(billId);
            return true;
        }
        return false;
    }

    @Override
    public List<Bill> findByUser(Long userId) {
        return billDAO.findByUser(userId);
    }

    @Override
    public void createWithDetail(Bill bill, BillDetail detail) {
        billDAO.createWithDetail(bill, detail);
    }
    @Override
    public List<Bill> findAll() {
        // Chúng ta nên dùng một câu JPQL riêng để sắp xếp đơn mới nhất lên đầu
        return billDAO.findAll();
    }
    @Override
    public void checkout(Bill bill, Map<Long, CartItem> cart) {
        // 1. Lưu hóa đơn chính trước để lấy ID
        billDAO.create(bill);

        // 2. Duyệt giỏ hàng để lưu từng chi tiết hóa đơn
        for (CartItem item : cart.values()) {
            BillDetail detail = new BillDetail();
            detail.setBill(bill);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());

            Drink d = new Drink();
            d.setId(item.getId());
            detail.setDrink(d);

            detailDAO.create(detail);
        }
    }

    @Override
    public Bill findById(Long id) { return billDAO.findById(id); }

    @Override
    public void update(Bill bill) { billDAO.update(bill); }

    @Override
    public List<BillDetail> findDetailsByBillId(Long billId) {
        return detailDAO.findByBillId(billId); // Gọi xuống DetailDAO
    }

    @Override
    public List<Bill> getBills(int page, int pageSize) {
        return billDAO.findAllPaginated(page, pageSize);
    }

    @Override
    public int countBillPages(int pageSize) {
        long total = billDAO.count();
        return (int) Math.ceil((double) total / pageSize);
    }
}