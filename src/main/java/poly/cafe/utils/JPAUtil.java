package poly.cafe.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "PolyCafePU";

    private static final EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            System.out.println("Kết nối database thành công!");
            return factory;
        } catch (Exception e) {
            System.err.println("Kết nối database thất bại!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("Đã đóng kết nối database.");
        }
    }
}