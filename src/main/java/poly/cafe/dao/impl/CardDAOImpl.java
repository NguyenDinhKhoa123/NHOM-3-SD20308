package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.CardDAO;
import poly.cafe.entity.Card;
import poly.cafe.utils.JPAUtil;

import java.util.List;

public class CardDAOImpl extends GenericDAOImpl<Card, Long> implements CardDAO {

    public CardDAOImpl() {
        super(Card.class);
    }

    @Override
    public List<Card> findAvailable() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Card c WHERE c.status = 'free'";
            TypedQuery<Card> query = em.createQuery(jpql, Card.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}