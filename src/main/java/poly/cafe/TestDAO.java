package poly.cafe;

import poly.cafe.dao.*;
import poly.cafe.dao.impl.*;
import poly.cafe.entity.BillDetail;
import poly.cafe.entity.Drink;
import poly.cafe.entity.User;
import poly.cafe.utils.JPAUtil;

public class TestDAO {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOImpl();
        DrinkDAO drinkDAO = new DrinkDAOImpl();
        CardDAO cardDAO = new CardDAOImpl();
        BillDAO billDAO = new BillDAOImpl();
        BillDetail billDetail = new BillDetail();

        userDAO.findAll().forEach(System.out::println);




//        Drink drink = Drink.builder()
//                .name("Test Drink")
//                .price(20000.0)
//                .build();
//
//        new DrinkDAOImpl().create(drink);

//        DrinkDAOImpl dao = new DrinkDAOImpl();
//
//        Drink d = dao.findById(1L);
//        d.setPrice(50000.0);
//
//        dao.update(d);
//
//        dao.delete(1L);

//        Class<User> clazz = User.class;
//        String field = "email";
//        String value = "admin@gmail.com";
//
//        JPAUtil.getEntityManager()
//                .createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE e." + field + " = :value", clazz)
//                .setParameter("value", value)
//                .getResultList()
//                .forEach(System.out::println);
//
//        JPAUtil.getEntityManager()
//                .createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE e." + field + " LIKE :kw", clazz)
//                .setParameter("kw", "%" + value + "%")
//                .getResultList()
//                .forEach(System.out::println);
    }
}