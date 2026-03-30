package poly.cafe.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*") // Đường dẫn sẽ có dạng: /PolyCafe/images/ten_anh.jpg
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy tên file ảnh từ URL (Ví dụ: /ep_carot.jpg)
        String imageName = req.getPathInfo();
        if (imageName == null || imageName.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. Xác định đường dẫn tuyệt đối đến thư mục uploads
        // Nó sẽ trỏ đúng vào thư mục thực tế đang chạy trên server
        String uploadPath = getServletContext().getRealPath("/uploads");
        File file = new File(uploadPath, imageName);

        // 3. Kiểm tra file có tồn tại không
        if (!file.exists()) {
            // Nếu không có ảnh, có thể trả về một ảnh mặc định "no-image.png"
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 4. Thiết lập kiểu dữ liệu trả về (image/jpeg, image/png, ...)
        String contentType = getServletContext().getMimeType(file.getName());
        resp.setContentType(contentType);
        resp.setHeader("Content-Length", String.valueOf(file.length()));

        // 5. Đọc file và ghi vào luồng xuất (OutputStream) của Response
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}