package poly.cafe.dao;

import poly.cafe.entity.BillDetail;
import java.util.List;

public interface BillDetailDAO extends GenericDAO<BillDetail, Long> {

    List<BillDetail> findByBillId(Long billId);
}