import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Department department = new Department(1,"Recursos Humanos");
        Seller seller = new Seller(1,"Thainan","thainan@gmail.com",new Date(),4000.0,department);

        System.out.println(seller);
    }
}