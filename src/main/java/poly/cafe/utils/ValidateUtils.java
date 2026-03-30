package poly.cafe.utils;

public class ValidateUtils {

    /**
     * Kiểm tra chuỗi bị rỗng hoặc null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra định dạng Email hợp lệ
     */
    public static boolean isEmail(String email) {
        if (isEmpty(email)) return false;
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    /**
     * Kiểm tra số tiền hoặc số lượng phải lớn hơn 0
     */
    public static boolean isGreaterThanZero(Double number) {
        return number != null && number > 0;
    }

    public static boolean isGreaterThanZero(Integer number) {
        return number != null && number > 0;
    }
}