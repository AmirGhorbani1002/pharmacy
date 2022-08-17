package view;

import check.Check;
import service.admin.AdminServiceImpl;
import view.admin.AdminMethods;

import java.util.Objects;
import java.util.Scanner;

public class AdminMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminMethods adminMethods = new AdminMethods(new AdminServiceImpl());

    public void showMenu() {
        while(true){
            System.out.println("1) Add Drug");
            System.out.println("2) See prescriptions");
            System.out.println("3) Exit");
            System.out.print("Enter command: ");
            String command = scanner.next();
            if(Objects.equals(command, "1")){
                adminMethods.addDrug();
                System.out.println("Done! ");
                Check.pressEnter();
            } else if(Objects.equals(command, "2")){
                adminMethods.showPrescriptions();
            } else if(Objects.equals(command, "3")){
                break;
            } else{
                System.out.println("wrong input");
            }
        }
    }

}
