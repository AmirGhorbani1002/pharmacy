package view;

import entity.Patient;
import repository.drug.DrugRepositoryImpl;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;
import repository.receipt.ReceiptRepositoryImpl;
import service.drug.DrugServiceImpl;
import service.patient.PatientServiceImpl;
import service.patient.interfaces.PatientService;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;

import java.util.Objects;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PatientService patientService;
    private final PatientMenu patientMenu = new PatientMenu(new PatientServiceImpl(
            new PatientRepositoryImpl(),
            new PrescriptionServiceImpl(new PrescriptionRepositoryImpl()),
            new ReceiptServiceImpl(new ReceiptRepositoryImpl()),
            new DrugServiceImpl(new DrugRepositoryImpl())
    ));
    private final AdminMenu adminMenu = new AdminMenu();

    public MainMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("Declare your user type");
            String userType = scanner.next();
            if (Objects.equals(userType, "patient")) {
                System.out.print("Enter your firstname: ");
                String firstname = scanner.next();
                System.out.print("Enter your lastname: ");
                String lastname = scanner.next();
                System.out.print("Enter your national code: ");
                String nationalCode = scanner.next();
                Patient patient = new Patient(firstname, lastname, nationalCode);
                patient = patientService.load(patient);
                patientMenu.showMenu(patient);
            } else if(Objects.equals(userType, "admin")){
                adminMenu.showMenu();
            } else{
                System.out.println("Just enter patient or admin");
            }
        }
    }

}
