package poly.cafe.dao;

import poly.cafe.entity.Card;
import java.util.List;

public interface CardDAO extends GenericDAO<Card, Long> {

    List<Card> findAvailable();
}