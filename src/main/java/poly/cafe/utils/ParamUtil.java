package poly.cafe.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParamUtil {
    /**
     * Lấy chuỗi từ Parameter, nếu trống thì trả về giá trị mặc định
     */
    public static String getString(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }

    /**
     * Lấy số nguyên (int), nếu lỗi hoặc trống trả về giá trị mặc định
     */
    public static int getInt(HttpServletRequest request, String name, int defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Lấy số thực (double), dùng cho giá tiền
     */
    public static double getDouble(HttpServletRequest request, String name, double defaultValue) {
        try {
            return Double.parseDouble(request.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Lấy giá trị Boolean (Checkbox)
     */
    public static boolean getBoolean(HttpServletRequest request, String name, boolean defaultValue) {
        String value = request.getParameter(name);
        if (value != null) {
            return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on") || value.equals("1");
        }
        return defaultValue;
    }

    /**
     * Lấy ngày tháng (Date) với định dạng mặc định yyyy-MM-dd
     */
    public static Date getDate(HttpServletRequest request, String name, String pattern) {
        try {
            String value = request.getParameter(name);
            return new SimpleDateFormat(pattern).parse(value);
        } catch (Exception e) {
            return null; // Hoặc trả về ngày hiện tại tùy logic của bạn
        }
    }
}