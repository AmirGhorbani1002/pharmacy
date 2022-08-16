package view.admin;

import entity.Drug;
import service.admin.interfaces.AdminService;

import java.util.Scanner;

public class AdminMethods {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminService adminService;

    public AdminMethods(AdminService adminService) {
        this.adminService = adminService;
    }

    public void showPrescriptions(){
        System.out.println("These prescriptions require your approval");
        adminService.loadAllPendingPrescription();
        System.out.println("Enter one of the Id's to check");
        int id = scanner.nextInt();
        adminService.loadPrescription(id);
        for (int i = 0; i < adminService.numberOfDrugs(); i++) {
            int status = adminService.loadPrescriptionsDrugs(id, i);
            if (status == -1) {
                System.out.println("We dont have this drug in our stock");
                System.out.println("Press enter to continue");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.print("How many of these drug do you give to the patient? ");
                int number = scanner.nextInt();
                adminService.approvalOfTheDrug(id, i, number);
            }
        }
        adminService.confirmPrescription(id);
    }

    public void addDrug(){
        System.out.print("Enter the name of the drug: ");
        String name = scanner.nextLine();
        System.out.print("Enter the price of drug: ");
        String temp = scanner.next();
        float price = 0;
        try{
            price = Float.parseFloat(temp);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        System.out.print("Enter the number of drug: ");
        String temp2 = scanner.next();
        int number = 0;
        try{
            number = Integer.parseInt(temp2);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        Drug drug = new Drug(-1,name,price,number);
        adminService.addDrug(drug);
    }

}
