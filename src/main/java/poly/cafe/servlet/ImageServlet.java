package poly.cafe.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String imageName = req.getPathInfo();
        if (imageName == null || imageName.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // BƯỚC QUAN TRỌNG: Sửa đường dẫn để khớp với thư mục mà FileUtil đang lưu vào
        String uploadPath = getServletContext().getRealPath("/uploads");

        // Nếu bạn muốn ImageServlet đọc được cả ảnh trong thư mục "images" mặc định của project:
        File file = new File(uploadPath, imageName);
        if (!file.exists()) {
            // Thử tìm trong thư mục gốc /uploads nếu không thấy trong /uploads/drinks
            uploadPath = getServletContext().getRealPath("/uploads");
            file = new File(uploadPath, imageName);
        }

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType(getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}