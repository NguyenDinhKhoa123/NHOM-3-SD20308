package poly.cafe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import poly.cafe.entity.User;

public class AuthUtil {

    /**
     * Lưu thông tin người dùng vào Session sau khi đăng nhập thành công
     */
    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    /**
     * Lấy thông tin người dùng từ Session
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập hay chưa
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    /**
     * Kiểm tra xem người dùng có phải là Admin (Quản trị viên) không
     */
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        if (user != null) {
            return user.getRole().toLowerCase().equals("admin");
        }
        return false;
    }

    /**
     * Kiểm tra xem người dùng có quyền Staff (Nhân viên) không.
     * Lưu ý: Admin cũng được coi là có quyền của Staff để duyệt đơn.
     */
    public static boolean isStaff(HttpServletRequest request) {
        User user = getUser(request);
        if (user != null) {
            String role = user.getRole().toLowerCase();
            return role.equals("staff") || role.equals("admin");
        }
        return false;
    }

    /**
     * Đăng xuất: Xóa thông tin người dùng khỏi Session
     */
    public static void clear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
    }
}