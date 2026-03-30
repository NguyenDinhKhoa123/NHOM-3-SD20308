package poly.cafe.service.impl;

import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl;
import poly.cafe.entity.User;
import poly.cafe.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    // Tiêm DAO vào Service
    private UserDAO dao = new UserDAOImpl();

    @Override
    public User login(String email, String password) {
        // Bạn có thể thêm logic kiểm tra email null ở đây
        return dao.login(email, password);
    }

    @Override
    public User register(User user) {
        // Kiểm tra email tồn tại chưa trước khi tạo
        if (dao.findByEmail(user.getEmail()) != null) {
            return null;
        }
        user.setRole("customer"); // Mặc định đăng ký là khách hàng
        user.setActive(true);
        return dao.create(user);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }
}