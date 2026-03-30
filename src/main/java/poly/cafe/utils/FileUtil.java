package poly.cafe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.UUID;

public class FileUtil {
    public static String save(HttpServletRequest request, String paramName, String folder) {
        try {
            Part part = request.getPart(paramName);
            if (part == null || part.getSize() == 0) return null;

            String realPath = request.getServletContext().getRealPath("/" + folder);
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();

            // Đổi tên file sang UUID để không bị trùng
            String originName = part.getSubmittedFileName();
            String ext = originName.substring(originName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + ext;

            part.write(realPath + File.separator + fileName);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }
}