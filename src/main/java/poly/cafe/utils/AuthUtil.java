package poly.cafe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import poly.cafe.entity.User;

public class AuthUtil {

    public static void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && user.getRole().equalsIgnoreCase("admin");
    }

    public static boolean isStaff(HttpServletRequest request) {
        User user = getUser(request);
        if (user == null) return false;
        String role = user.getRole().toLowerCase();
        return role.equals("staff") || role.equals("admin");
    }

    public static void clear(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }
}