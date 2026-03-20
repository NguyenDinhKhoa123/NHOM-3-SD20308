package poly.cafe.dao;

import poly.cafe.entity.Bill;
import java.util.List;

public interface BillDAO extends GenericDAO<Bill, Long> {

    Bill findByCard(Long cardId);

    void updateStatus(Long billId, String status);
}