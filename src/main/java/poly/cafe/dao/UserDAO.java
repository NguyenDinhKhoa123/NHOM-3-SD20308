package poly.cafe.dao;

import poly.cafe.entity.User;

public interface UserDAO extends GenericDAO<User, Long> {

    User findByEmail(String email);

    User login(String email, String password);
}