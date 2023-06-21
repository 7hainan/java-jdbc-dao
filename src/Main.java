import model.entities.*;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DepartmentPrompt departmentPrompt = PromptFactory.createDepartmentPrompt();
        SellerPrompt sellerPrompt = PromptFactory.createSellerPrompt();

        System.out.println("::::::::::::::JAVA-JDBC-DAO::::::::::::::");
        System.out.println("PEAK ONE OF THESE OPTIONS BELOW");
        System.out.println();

        System.out.println("1 - DEPARTMENTS");
        System.out.println("2 - SELLER´S");
        System.out.println("3 - EXIT");
        int option = sc.nextInt();
        switch (option){
            case 1:
                departmentPrompt.executePrompt();
                break;
            case 2:
                sellerPrompt.executePrompt();
                break;
            case 3:
                sc.close();
                break;
        }
        sc.close();
    }
}