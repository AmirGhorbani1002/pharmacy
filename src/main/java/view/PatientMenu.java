package view;

import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import service.patient.PatientServiceImpl;
import util.list.MyList;

import java.util.Objects;
import java.util.Scanner;

public class PatientMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PatientServiceImpl patientService = new PatientServiceImpl();

    public void showMenu(Patient patient) {

        Prescription prescription = patientService.loadPrescription(patient);
        if (prescription == null) {
            System.out.println("Our pharmacy has some limitations!!! You can choose up to 10 drugs");
            patient.setPrescription(new Prescription(patient.getId()));
            patientService.addPrescription(patient);
            patient.setPrescription(patientService.loadPrescription(patient));
            for (int i = 10; i > 0; i--) {
                System.out.println("Remaining: " + i);
                System.out.print("Enter the name of the drug you need: ");
                String name = scanner.nextLine();
                System.out.print("How much of this drug do you need?");
                int count = scanner.nextInt();
                SimpleDrug drug = new SimpleDrug(name, count);
                patientService.addDrug(patient, drug);
                System.out.print("Do you need any other drug? Enter \"yes\" to continue otherwise Enter \"no\":  ");
                String command = scanner.next();
                if (Objects.equals(command, "no"))
                    break;
            }
            System.out.println("Done. Now wait for our admin to check your prescription");
        } else if (prescription.getStatus() == PrescriptionStatus.PENDING) {
            System.out.println("Please wait. Our admin has not checked your prescription yet");
        } else if (prescription.getStatus() == PrescriptionStatus.ACCEPT) {
            while (true) {
                patient.setPrescription(patientService.loadPrescription(patient));
                patient.getPrescription().setDrugs(new MyList<>());
                System.out.println(patient.getPrescription().getId());
                patient.getPrescription().getDrugs().setItems(patientService.
                        loadPrescriptionsDrugs(patient.getPrescription().getId()));
                Receipt receipt = patientService.loadReceipt(patient.getId());
                for (int i = 0; i < receipt.getDrugs().size(); i++) {
                    int myCount = receipt.getDrugs().getItem(i).getCount();
                    int patientCount = patient.getPrescription().getDrugs().getItem(i).getCount();
                    if (myCount == 0) {
                        System.out.println((i + 1) + ") We dont have a drug with " + receipt.getDrugs().getItem(i).getName() + "'s name");
                    } else {
                        if (myCount < patientCount) {
                            System.out.print((i + 1) + ") For " + receipt.getDrugs().getItem(i).getName() +
                                    " drug, you want " + patientCount + ", But we have " + myCount + " number.");
                            System.out.println(" Price is: " + receipt.getDrugs().getItem(i).getPrice() + " Toman");
                        } else {
                            System.out.print((i + 1) + ") For " + receipt.getDrugs().getItem(i).getName() +
                                    " drug, We have enough");
                            System.out.println(" Price is: " + receipt.getDrugs().getItem(i).getPrice() + " Toman");
                        }
                    }
                }
                System.out.print("If you want to delete something, Enter the id otherwise Enter 0: ");
                int deleteId = scanner.nextInt();
                if (deleteId == 0) {
                    break;
                }
                receipt.getDrugs().remove(deleteId - 1);
            }
        }
    }

}
