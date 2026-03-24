package poly.cafe;

import poly.cafe.dao.*;
import poly.cafe.dao.impl.*;
import poly.cafe.entity.BillDetail;

public class TestDAO {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOImpl();
        DrinkDAO drinkDAO = new DrinkDAOImpl();
        CardDAO cardDAO = new CardDAOImpl();
        BillDAO billDAO = new BillDAOImpl();
        BillDetail billDetail = new BillDetail();

        userDAO.findAll().forEach(System.out::println);


    }
}