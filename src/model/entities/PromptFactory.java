package model.entities;

import java.util.Scanner;

public class PromptFactory {
    public static DepartmentPrompt createDepartmentPrompt(){
        return new DepartmentPrompt(new Scanner(System.in));
    }
    public static SellerPrompt createSellerPrompt(){return new SellerPrompt(new Scanner(System.in));}
}
