package view;

import entity.Admin;
import service.admin.AdminServiceImpl;

import java.util.Scanner;

public class AdminMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminServiceImpl adminService = new AdminServiceImpl();

    public void showMenu() {
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

}
