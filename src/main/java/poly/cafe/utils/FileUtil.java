package poly.cafe.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FileUtil {

    /**
     * Lưu file upload từ Form vào thư mục chỉ định
     * @param request HttpServletRequest từ Servlet
     * @param paramName Tên của input type="file" trong Form
     * @param folder Tên thư mục con trong webapp (ví dụ: "uploads")
     * @return Tên file đã lưu thành công, hoặc null nếu thất bại
     */
    public static String save(HttpServletRequest request, String paramName, String folder) {
        try {
            Part part = request.getPart(paramName);
            if (part == null || part.getSize() == 0) return null;

            // 1. Xác định đường dẫn tuyệt đối đến thư mục chứa ảnh
            String realPath = request.getServletContext().getRealPath("/" + folder);
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs(); // Tự động tạo thư mục nếu chưa có

            // 2. Lấy tên file gốc và lưu
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            // Mẹo: Bạn có thể cộng thêm System.currentTimeMillis() vào tên file để tránh trùng

            part.write(realPath + File.separator + fileName);
            return fileName;
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Xóa một file khỏi hệ thống
     * @param request HttpServletRequest
     * @param fileName Tên file cần xóa
     * @param folder Thư mục chứa file
     * @return true nếu xóa thành công
     */
    public static boolean delete(HttpServletRequest request, String fileName, String folder) {
        if (fileName == null || fileName.isEmpty()) return false;

        String realPath = request.getServletContext().getRealPath("/" + folder);
        Path path = Paths.get(realPath, fileName);

        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}