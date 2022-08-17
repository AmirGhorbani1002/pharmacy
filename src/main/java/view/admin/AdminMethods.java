package view.admin;

import check.Check;
import entity.Drug;
import entity.Prescription;
import service.admin.interfaces.AdminService;
import util.list.MyList;

import java.util.Scanner;

public class AdminMethods {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminService adminService;

    private final Check check = new Check();

    public AdminMethods(AdminService adminService) {
        this.adminService = adminService;
    }

    public void showPrescriptions() {
        System.out.println("These prescriptions require your approval");
        Prescription[] prescriptions = adminService.loadAllPendingPrescription();
        if(prescriptions.length == 0)
            return;
         MyList<Prescription> prescriptionMyList = new MyList<>();
         prescriptionMyList.setItems(prescriptions);
        System.out.println(prescriptionMyList);
        Check.pressEnter();
        selectPrescription();
    }

    public void addDrug() {
        System.out.print("Enter the name of the drug: ");
        String name = scanner.nextLine();
        System.out.print("Enter the price of drug: ");
        float price = check.checkPrice(scanner.next());
        System.out.print("Enter the number of drug: ");
        int number = check.checkCorrectInt(scanner.next());
        Drug drug = new Drug(-1, name, price, number);
        adminService.addDrug(drug);
    }

    private void selectPrescription() {
        System.out.println("Enter one of the Id's to check");
        int id = check.checkCorrectInt(scanner.next());
        if (!adminService.loadPrescription(id)) {
            System.out.println("Wrong id");
            return;
        }
        for (int i = 0; i < adminService.numberOfDrugs(); i++) {
            int[] status = adminService.loadPrescriptionsDrugs(id, i);
            if (status[0] == -1) {
                System.out.println("We dont have this drug in our stock");
                Check.pressEnter();
            } else {
                System.out.print("How many of these drug do you give to the patient? ");
                int number = check.checkCorrectInt(scanner.next());

                while (true) {
                    if(number > status[1]){
                        System.out.print("We do not have this number!!! Enter again: ");
                        number = check.checkCorrectInt(scanner.next());
                    } else break;
                }
                adminService.approvalOfTheDrug(id, i, number);
            }
        }
        adminService.confirmPrescription(id);
    }

}
