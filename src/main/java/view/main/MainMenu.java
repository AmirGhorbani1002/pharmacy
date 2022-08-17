package view.main;

import entity.Patient;
import repository.drug.DrugRepositoryImpl;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;
import repository.receipt.ReceiptRepositoryImpl;
import service.admin.interfaces.AdminService;
import service.drug.DrugServiceImpl;
import service.patient.PatientServiceImpl;
import service.patient.interfaces.PatientService;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;
import view.patient.PatientMenu;
import view.admin.AdminMenu;

import java.util.Objects;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PatientService patientService;

    private final AdminService adminService;
    private final PatientMenu patientMenu = new PatientMenu(new PatientServiceImpl(
            new PatientRepositoryImpl(),
            new PrescriptionServiceImpl(new PrescriptionRepositoryImpl()),
            new ReceiptServiceImpl(new ReceiptRepositoryImpl()),
            new DrugServiceImpl(new DrugRepositoryImpl())
    ));
    private final AdminMenu adminMenu = new AdminMenu();

    public MainMenu(PatientService patientService, AdminService adminService) {
        this.patientService = patientService;
        this.adminService = adminService;
    }

    public void showMenu() {
        while (true) {
            System.out.print("Declare your user type or exit: ");
            String userType = scanner.next();
            if (Objects.equals(userType, "patient")) {
                System.out.print("Enter your national code: ");
                String nationalCode = scanner.next();
                Patient patient = patientService.load(nationalCode);
                if(patient == null){
                    System.out.print("Enter your firstname: ");
                    String firstname = scanner.next();
                    System.out.print("Enter your lastname: ");
                    String lastname = scanner.next();
                    patient = new Patient(firstname,lastname,nationalCode);
                    patientService.save(patient);
                    patient = patientService.load(patient.getNationalCode());
                }
                patientMenu.showMenu(patient);
            } else if(Objects.equals(userType, "admin")){
                System.out.print("Enter admin password: ");
                String password = scanner.next();
                if(adminService.load(password))
                    adminMenu.showMenu();
                else
                    System.out.println("Wrong password");
            } else if(Objects.equals(userType, "exit")){
                break;
            } else{
                System.out.println("Just enter patient or admin");
            }
        }
    }

}
