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

        String uploadPath = getServletContext().getRealPath("/uploads");
        File file = new File(uploadPath, imageName);

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType(getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}