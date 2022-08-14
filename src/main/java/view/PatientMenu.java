package view;

import entity.Patient;
import entity.Prescription;
import entity.SimpleDrug;
import repository.patient.PatientRepositoryImpl;
import service.patient.PatientServiceImpl;

import java.util.Objects;
import java.util.Scanner;

public class PatientMenu {

    private Scanner scanner = new Scanner(System.in);
    private final PatientServiceImpl patientService = new PatientServiceImpl();
    public void showMenu(Patient patient){

        Prescription prescription = patientService.loadPrescription(patient);
        if(prescription == null){
            System.out.println("Our pharmacy has some limitations!!! You can choose up to 10 drugs");
            patient.setPrescription(new Prescription(patient.getId()));
            for(int i=10;i>0;i--){
                System.out.println("Remaining: " + i);
                System.out.print("Enter the name of the medicine you need: ");
                String name = scanner.nextLine();
                System.out.print("How much of this medicine do you need?");
                int count = scanner.nextInt();
                SimpleDrug drug = new SimpleDrug(name,count);
                patient.getPrescription().getDrugs().add(drug);
                System.out.print("Do you need any other medicine? Enter \"yes\" to continue otherwise Enter \"no\":  ");
                String command = scanner.next();
                if(Objects.equals(command, "no"))
                    break;
            }

        }
    }

}
