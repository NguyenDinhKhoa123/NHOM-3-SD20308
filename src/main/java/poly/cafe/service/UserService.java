package poly.cafe.service;

import poly.cafe.entity.User;

import java.util.List;

public interface UserService {
    User login(String email, String password);

    User register(User user);

    List<User> findAll();
}
