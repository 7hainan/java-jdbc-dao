package model.entities;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.utils.PromptUtils;

import java.util.List;
import java.util.Scanner;

public class DepartmentPrompt implements PromptFunctions{
    private final Scanner sc;
    private final DepartmentDao departmentDao;

    public DepartmentPrompt(Scanner sc) {
        this.sc = sc;
        this.departmentDao = DaoFactory.createDepartmentDao();
    }

    @Override
    public void executePrompt() {
        initialPrompt();
    }
    @Override
    public void initialPrompt() {
        PromptUtils.clearConsole();
        Scanner sc = new Scanner(System.in);
        System.out.println(":::::::::DEPARTMENT REGISTRY::::::::");
        System.out.println("1 - REGISTRY AN DEPARTMENT");
        System.out.println("2 - SEARCH AN DEPARTMENT BY ID");
        System.out.println("3 - LIST ALL DEPARTMENTS");
        System.out.println("4 - UPDATE AN DEPARTMENT");
        System.out.println("5 - DELETE DEPARTMENT BY ID");
        System.out.println("0 - EXIT");
        int option = sc.nextInt();

        switch (option){
            case 1 :
                promptDepartmentRegistry();
                break;
            case 2:
                promptSearchDepartmentById();
                break;
            case 3:
                promptListAllDepartment();
                break;
            case 4:
                promptUpdateDepartment();
                break;
            case 5:
                promptDeleteDepartmentById();
                break;
            case 0:
                sc.close();
                break;
        }

        sc.close();
    }
    public void promptUpdateDepartment() {
        System.out.println(":::::::::DEPARTMENT LIST::::::::");
        List<Department> departments = departmentDao.findAll();
        System.out.println("=======================================");
        departments.forEach(System.out::println);
        System.out.println("=======================================");

        System.out.print("ID OF DEPARTMENT FOR UPDATE: ");
        int departmentId = sc.nextInt();

        Department department = departmentDao.findById(departmentId);

        if(department == null){
            System.out.println("DEPARTMENT NOT FOUNDED");
            PromptUtils.clearConsole();
            backToMainMenu();
        }else{
            System.out.print("NEW NAME FOR DEPARTMENT: ");
            department.setName(sc.next());

            departmentDao.update(department);
            System.out.println("DEPARTMENT HAS UPDATED!!!");
            backToMainMenu();
        }

    }

    public void promptDeleteDepartmentById() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::DEPARTMENT LIST::::::::");
        List<Department> departments = departmentDao.findAll();
        System.out.println("=======================================");
        departments.forEach(System.out::println);
        System.out.println("=======================================");

        System.out.print("INSERT THE ID OF DEPARTMENT FOR DELETE: ");
        int departmentId = sc.nextInt();
        departmentDao.deleteById(departmentId);
        System.out.println("DEPARTMENT HAS DELETED!!!");
        backToMainMenu();

    }

    public void promptListAllDepartment() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::DEPARTMENTS LIST::::::::");
        List<Department> departments = departmentDao.findAll();

        if(departments.isEmpty()){
            System.out.println("NO DEPARTMENTS IN DB");
            PromptUtils.clearConsole();
            initialPrompt();
        }

        departments.forEach(System.out::println);
        backToMainMenu();
    }

    public void promptSearchDepartmentById() {
        PromptUtils.clearConsole();
        System.out.println(":::::::::DEPARTMENT REGISTRY::::::::");
        System.out.print("TYPE AN ID OF A EXISTING DEPARTMENT: ");
        int idDepartment = sc.nextInt();

        Department department = departmentDao.findById(idDepartment);

        if(department == null){
            System.out.println("ID NOT FOUNDED!!");
            PromptUtils.clearConsole();
            initialPrompt();
        }

        System.out.println("DEPARTMENT FOUND: "+ department);
        backToMainMenu();
    }



    public void promptDepartmentRegistry() {

        PromptUtils.clearConsole();
        System.out.println(":::::::::DEPARTMENT REGISTRY::::::::");
        System.out.print("INSERT THE NAME OF THE DEPARTMENT: ");
        String departmentName = sc.nextLine();

        if(departmentName.trim().length() == 0){
            do{
                System.out.println("Please insert an department name!");
                PromptUtils.clearConsole();
                System.out.println(":::::::::DEPARTMENT REGISTRY::::::::");
                System.out.print("Insert the new department name: ");
                departmentName = sc.nextLine();
            }while (departmentName.trim().length() == 0);
        }

        //Registry department
        Department department = new Department(departmentName);
        departmentDao.insert(department);
        System.out.println("DEPARTMENT NAME: "+ department.getName() + " REGISTERED!!");
        PromptUtils.clearConsole();
        initialPrompt();
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
