package poly.cafe.service.impl;

import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl;
import poly.cafe.entity.User;
import poly.cafe.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO dao = new UserDAOImpl();

    @Override
    public User login(String email, String password) {
        return dao.login(email, password);
    }

    @Override
    public User register(User user) {
        if (dao.findByEmail(user.getEmail()) != null) {
            return null;
        }
        user.setRole("customer");
        user.setActive(true);
        return dao.create(user);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<User> search(String keyword, String role) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.findAll();
        }
        return dao.findByFullnameAndRole(keyword.trim(), role);
    }
}