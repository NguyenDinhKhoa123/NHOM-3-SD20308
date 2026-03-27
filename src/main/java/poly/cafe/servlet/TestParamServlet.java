package poly.cafe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.cafe.utils.ParamUtil;

@WebServlet("/test-util")
public class TestParamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị một Form đơn giản ngay trong Servlet để test nhanh
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<h2>Test UTF-8 & ParamUtil</h2>");
        out.println("<form method='POST'>");
        out.println("  Nhập tiếng Việt: <input type='text' name='txtTiengViet' value='Cà phê sữa đá'><br><br>");
        out.println("  Nhập số (Tuổi): <input type='text' name='txtTuoi' value='20'><br><br>");
        out.println("  Check (Kích hoạt): <input type='checkbox' name='chkStatus' checked><br><br>");
        out.println("  <button type='submit'>Gửi dữ liệu Test</button>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // --- ĐÂY LÀ PHẦN TEST CHÍNH ---

        // 1. Test UTF-8 & ParamUtil.getString
        String hoten = ParamUtil.getString(req, "txtTiengViet", "N/A");

        // 2. Test ParamUtil.getInt (Thử nhập chữ vào ô tuổi để xem nó có crash không)
        int tuoi = ParamUtil.getInt(req, "txtTuoi", 0);

        // 3. Test ParamUtil.getBoolean
        boolean status = ParamUtil.getBoolean(req, "chkStatus", false);

        // Xuất kết quả ra màn hình
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h3>Kết quả xử lý:</h3>");
        out.println("<ul>");
        out.println("  <li><b>Họ tên (UTF-8):</b> " + hoten + "</li>");
        out.println("  <li><b>Tuổi (Integer):</b> " + tuoi + "</li>");
        out.println("  <li><b>Trạng thái (Boolean):</b> " + status + "</li>");
        out.println("</ul>");
        out.println("<a href='test-util'>Quay lại</a>");
    }
}