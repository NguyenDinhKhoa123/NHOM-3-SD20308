package poly.cafe.dao;

import poly.cafe.entity.User;
import java.util.List;

public interface UserDAO extends GenericDAO<User, Long> {
    User findByEmail(String email);
    User login(String email, String password);

    List<User> findByFullnameAndRole(String fullname, String role);

}