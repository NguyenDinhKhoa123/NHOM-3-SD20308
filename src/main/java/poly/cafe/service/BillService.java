package poly.cafe.service;

import poly.cafe.entity.Bill;
import poly.cafe.model.CartItem;
import java.util.Map;

public interface BillService {
    void checkout(Bill bill, Map<Long, CartItem> cart);
}