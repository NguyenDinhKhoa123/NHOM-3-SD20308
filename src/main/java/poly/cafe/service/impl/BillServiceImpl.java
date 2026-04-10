package poly.cafe.service.impl;

import poly.cafe.dao.BillDAO;
import poly.cafe.dao.BillDetailDAO;
import poly.cafe.dao.impl.BillDAOImpl;
import poly.cafe.dao.impl.BillDetailDAOImpl;
import poly.cafe.entity.Bill;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.Drink;
import poly.cafe.entity.User;
import poly.cafe.model.CartItem;
import poly.cafe.service.BillService;
import java.util.List;
import java.util.Map;

public class BillServiceImpl implements BillService {

    private final BillDAO billDAO = new BillDAOImpl();
    private final BillDetailDAO detailDAO = new BillDetailDAOImpl();

    @Override
    public void updateStatus(Long id, String status) {
        Bill bill = billDAO.findById(id);
        if (bill != null) {
            bill.setStatus(status.trim());
            billDAO.update(bill);
        }
    }

    @Override
    public boolean cancelOrder(Long billId, Long userId) {
        Bill bill = billDAO.findById(billId);
        if (bill != null && bill.getUser().getId().equals(userId)) {
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
        return billDAO.findAll();
    }

    @Override
    public void checkout(Bill bill, Map<Long, CartItem> cart) {
        billDAO.create(bill);
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
    public List<Bill> getBillsForManagement(User currentUser, int page, int pageSize, boolean isHistory) {
        List<String> statuses = isHistory
                ? List.of("paid", "cancelled", "canceled")
                : List.of("pending", "confirmed");
        return billDAO.findBillsForManagement(currentUser, statuses, page, pageSize);
    }

    @Override
    public int countBillsForManagement(User currentUser, int pageSize, boolean isHistory) {
        List<String> statuses = isHistory
                ? List.of("paid", "cancelled", "canceled")
                : List.of("pending", "confirmed");
        long total = billDAO.countBillsForManagement(currentUser, statuses);
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public Bill findById(Long id) {
        return billDAO.findById(id);
    }

    @Override
    public void update(Bill bill) {
        billDAO.update(bill);
    }

    @Override
    public List<BillDetail> findDetailsByBillId(Long billId) {
        return detailDAO.findByBillId(billId);
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