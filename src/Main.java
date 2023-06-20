import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("======== TEST 01 FINDBYID =========");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(5);
        System.out.println(seller);

        System.out.println("\n ======== TEST 02 FINDBYDEPARTMENT =========");
        List<Seller> sellers = sellerDao.findByDeparment(new Department(2,null));
        sellers.forEach(System.out::println);

        System.out.println("\n ======== TEST 03 FINDALL =========");
        List<Seller> sellersAll = sellerDao.findAll();
        sellersAll.forEach(System.out::println);
    }
}