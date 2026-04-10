package poly.cafe.dao.impl;

import jakarta.persistence.*;
import poly.cafe.dao.UserDAO;
import poly.cafe.entity.User;
import poly.cafe.utils.JPAUtil;
import java.util.List;

public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {


    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public User login(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }


    @Override
    public List<User> findByFullnameAndRole(String fullname, String role) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE LOWER(u.fullname) LIKE LOWER(:fullname)";

            if (role != null && !role.isEmpty()) {
                jpql += " AND u.role = :role";
            }

            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("fullname", "%" + fullname + "%");

            if (role != null && !role.isEmpty()) {
                query.setParameter("role", role);
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

}