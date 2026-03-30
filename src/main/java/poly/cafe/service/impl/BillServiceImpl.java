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

import java.util.Map;

public class BillServiceImpl implements BillService {
    private BillDAO billDAO = new BillDAOImpl();
    private BillDetailDAO detailDAO = new BillDetailDAOImpl();

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
}