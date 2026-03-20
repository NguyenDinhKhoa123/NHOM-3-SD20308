package poly.cafe.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import poly.cafe.dao.UserDAO;
import poly.cafe.entity.User;
import poly.cafe.utils.JPAUtil;

public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public User login(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}