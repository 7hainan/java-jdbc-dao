import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //mainSeller();
        mainDepartment();
    }
    public static void mainDepartment(){
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("======== TEST 01 FINDBYID =========");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("\n======== TEST 02 FINDALL =========");
        List<Department> departments = departmentDao.findAll();
        departments.forEach(System.out::println);

        System.out.println("\n======== TEST 03 INSERT =========");
        Department newestDepartment = new Department("TI");
        departmentDao.insert(newestDepartment);
        System.out.println("Inserted!! new ID : " + newestDepartment.getId());

        System.out.println("\n======== TEST 04 UPDATE =========");
        Department departmentUpdate = departmentDao.findById(6);
        departmentUpdate.setName("TECNOLOGIA");
        departmentDao.update(departmentUpdate);

        System.out.println("\n ======== TEST 05 DELETEBYID =========");
        departmentDao.deleteById(6);


    }
    public static void mainSeller(){
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

        System.out.println("\n ======== TEST 04 INSERT =========");
        Seller newestSeller = new Seller("Elisangela","elisangela@gmail.com",new Date(),8000.0,new Department(3,null));
        sellerDao.insert(newestSeller);
        System.out.println("Inserted!! new ID : " + newestSeller.getId());

        System.out.println("\n ======== TEST 05 UPDATE =========");
        Seller sellerUpdate = sellerDao.findById(11);
        sellerUpdate.setName("Zeze polessa");
        sellerDao.update(sellerUpdate);

        System.out.println("\n ======== TEST 06 DELETEBYID =========");
        sellerDao.deleteById(13);
    }

}