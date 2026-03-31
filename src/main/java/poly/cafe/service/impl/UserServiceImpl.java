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

    // --- TRIỂN KHAI CÁC HÀM MỚI ---

    @Override
    public User findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(User user) {
        // Bạn có thể thêm logic kiểm tra trước khi update ở đây
        dao.update(user);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}