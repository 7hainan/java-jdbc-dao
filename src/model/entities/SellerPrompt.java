package model.entities;

import model.PromptException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.utils.PromptUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SellerPrompt implements PromptFunctions{
    private final Scanner sc;
    private final SellerDao sellerDao;
    public SellerPrompt(Scanner scanner) {
        this.sc = scanner;
        this.sellerDao = DaoFactory.createSellerDao();
    }

    @Override
    public void executePrompt() {
        initialPrompt();
    }
    @Override
    public void initialPrompt() {

        PromptUtils.clearConsole();
        Scanner sc = new Scanner(System.in);
        System.out.println(":::::::::SELLER REGISTRY::::::::");
        System.out.println("1 - REGISTRY A SELLER");
        System.out.println("2 - SEARCH AN SELLER BY ID");
        System.out.println("3 - LIST SELLER BY DEPARTMENT ID");
        System.out.println("4 - LIST ALL SELLERS");
        System.out.println("5 - UPDATE SELLER");
        System.out.println("6 - DELETE SELLER BY ID");
        System.out.println("0 - EXIT");
        int option = sc.nextInt();
        switch (option){
            case 1:
                sellerRegistry();
                break;
            case 2:
                findSellerById();
                break;
            case 3:
                listSellerByDepartment();
                break;
            case 4:
                listAllSellers();
                break;
            case 5:
                updateSeller();
                break;
            case 6:
                deleteSeller();
                break;
            case 0:
                sc.close();
                break;
        }
    }

    private void deleteSeller() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::SELLER LIST::::::::");

        List<Seller> sellers = sellerDao.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sellers.forEach(System.out::println);
        System.out.println(":::::::::::::::::::::::::::::");

        System.out.print("TYPE AN SELLER ID FOR DELETE : ");
        int sellerId = sc.nextInt();

        sellerDao.deleteById(sellerId);

        System.out.println("SELLER REGISTRY HAS BEN DELETED");

        backToMainMenu();
    }

    private void updateSeller() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::SELLER LIST::::::::");

        List<Seller> sellers = sellerDao.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sellers.forEach(System.out::println);
        System.out.println(":::::::::::::::::::::::::::::");


        System.out.print("PUT ID OF SELLER FOR UPDATE : ");
        int sellerId = sc.nextInt();
        Seller seller = sellerDao.findById(sellerId);
        sc.nextLine();

        if(seller == null){
            System.out.println("PLEASE PUT A EXISTING ID!!");
            backToMainMenu();
        }else{
            try {
                System.out.print("UPDATE NAME(" + seller.getName() + ")" + " : ");
                String newestName = sc.nextLine();

                System.out.print("UPDATE EMAIL(" + seller.getEmail() + ")" + " : ");
                String newestEmail = sc.nextLine();


                System.out.print("UPDATE BIRTHDATE(" + sdf.format(seller.getBirthDate()) + ")" + " : ");
                Date newestDate = sdf.parse(sc.nextLine());

                System.out.print("UPDATE BASESALARY(" + seller.getBaseSalary() + ")" + " : ");
                double newestSalary = sc.nextDouble();

                System.out.print("UPDATE DEPARTMENT ID(" + seller.getDepartment().getId() + ")" + " : ");
                int newestDepartmentId = sc.nextInt();

                seller.setName(newestName);
                seller.setEmail(newestEmail);
                seller.setBirthDate(newestDate);
                seller.setBaseSalary(newestSalary);
                seller.setDepartment(new Department(newestDepartmentId,null));
                sellerDao.update(seller);

                System.out.println("OPERATION SUCCESSFULLY");

                backToMainMenu();

            }catch (Exception e){
                throw new PromptException(e.getMessage());
            }

        }




    }

    private void listAllSellers() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::SELLER LIST::::::::");

        List<Seller> sellers = sellerDao.findAll();

        if(sellers.size() == 0){
            System.out.println("NO SELLERS IN THE DATABASE");
            backToMainMenu();
        }

        System.out.println(sellers.size()  + " SELLERS FOUNDED");
        sellers.forEach(System.out::println);
        backToMainMenu();
    }

    private void listSellerByDepartment() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::SELLER REGISTRY::::::::");
        System.out.print("TYPE AN ID OF A EXISTING DEPARTMENT: ");
        int departmentId = sc.nextInt();

        List<Seller> sellers = sellerDao.findByDeparment(new Department(departmentId,null));

        if(sellers.size() == 0){
            System.out.println("SELLER WITH THIS DEPARTMENT ID NOT FUNDED");
            PromptUtils.clearConsole();
            initialPrompt();
        }

        System.out.println(sellers.size()  + " SELLERS FOUNDED");
        sellers.forEach(System.out::println);
        backToMainMenu();
    }

    private void findSellerById() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::SELLER REGISTRY::::::::");
        System.out.print("TYPE AN ID OF A EXISTING SELLER: ");
        int sellerId = sc.nextInt();

        Seller seller = sellerDao.findById(sellerId);

        if(seller == null){
            System.out.println("ID NOT FOUNDED!!");
            PromptUtils.clearConsole();
            initialPrompt();
        }

        System.out.println("SELLER FOUNDED: "+ seller);
        backToMainMenu();
    }

    private void sellerRegistry(){
        try {
            System.out.println(":::::::::SELLER REGISTRY::::::::");
            System.out.print("INSERT THE SELLER NAME: ");
            String name = sc.nextLine();

            System.out.print("INSERT THE SELLER EMAIL: ");
            String email = sc.nextLine();

            System.out.print("INSERT THE SELLER BIRTHDATE(dd/mm/yyyy): ");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(sc.nextLine());

            System.out.print("INSERT THE SELLER BASE SALARY: ");
            double basesalary = sc.nextDouble();

            Seller seller = new Seller(name,email,date,basesalary,new Department(1,null));
            sellerDao.insert(seller);

            System.out.println("SELLER REGISTERED !! " + "ID : "+ seller.getId());
            backToMainMenu();



        }catch (Exception e){
            throw new PromptException(e.getMessage());
        }

    }
    @Override
    public void backToMainMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nBACK TO MAIN MENU - 0");
        int backToMenu = sc.nextInt();

        if(backToMenu == 0){
            PromptUtils.clearConsole();
            initialPrompt();
        }else{
            System.out.println("WRONG OPERATION!!");
            sc.close();
        }
    }


}
