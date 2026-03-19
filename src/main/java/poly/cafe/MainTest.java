package poly.cafe;

import poly.cafe.utils.JPAUtil;
import jakarta.persistence.EntityManager;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("========== KIỂM TRA KẾT NỐI DATABASE (JAKARTA) ==========");

        try {
            // Bước 1: Gọi EntityManager từ JPAUtil mà bạn đã viết
            EntityManager em = JPAUtil.getEntityManager();

            // Bước 2: Kiểm tra trạng thái
            if (em != null && em.isOpen()) {
                System.out.println(">>> TRẠNG THÁI: KẾT NỐI THÀNH CÔNG!");
                System.out.println(">>> Cấu hình Jakarta Persistence đã hoạt động.");

                // Đóng để giải phóng tài nguyên
                em.close();
            } else {
                System.err.println(">>> TRẠNG THÁI: KẾT NỐI THẤT BẠI (EntityManager is null or closed)");
            }

        } catch (Exception e) {
            System.err.println(">>> LỖI: Không thể khởi tạo EntityManager.");
            System.err.println(">>> Kiểm tra lại: Tên Persistence Unit trong JPAUtil và persistence.xml");
            e.printStackTrace();
        } finally {
            // Đóng Factory khi kết thúc test
            JPAUtil.shutdown();
            System.out.println("=========================================================");
        }
    }
}