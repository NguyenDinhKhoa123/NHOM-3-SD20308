package poly.cafe.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String PU_NAME = "PolyCafePU";
    private static final EntityManagerFactory emf = buildFactory();

    private static EntityManagerFactory buildFactory() {
        try {
            return Persistence.createEntityManagerFactory(PU_NAME);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo JPA: " + e.getMessage(), e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}