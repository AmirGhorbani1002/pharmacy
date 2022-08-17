package view;

import check.Check;
import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import entity.enums.ReceiptStatus;
import service.patient.interfaces.PatientService;
import util.list.MyList;

import java.util.Objects;
import java.util.Scanner;

public class PatientMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PatientService patientService;

    public PatientMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    private final Check check = new Check();

    public void showMenu(Patient patient) {

        Prescription prescription = patientService.loadPrescription(patient);
        // For when our patient does not have a pending prescription
        if (prescription == null) {
            System.out.println("Our pharmacy has some limitations!!! You can choose up to 10 drugs");
            addPrescriptionForPatient(patient);
            Check.pressEnter();
            for (int i = 10; i > 0; i--) {
                System.out.println("Remaining: " + i);
                System.out.print("Enter the name of the drug you need: ");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.print("How much of this drug do you need? ");
                int count = check.checkCorrectInt(scanner.next());
                SimpleDrug drug = new SimpleDrug(name, count);
                patientService.addDrug(patient, drug);
                System.out.print("Do you need any other drug? Enter \"yes\" to continue otherwise Enter \"no\":  ");
                String command = check.checkYesOrNo(scanner.next());
                if (Objects.equals(command, "no"))
                    break;
            }
            System.out.println("Done. Now wait for our admin to check your prescription");
            Check.pressEnter();
        } else if (prescription.getStatus() == PrescriptionStatus.PENDING) { // for when our patient has a pending prescription
            System.out.println("Please wait. Our admin has not checked your prescription yet");
            Check.pressEnter();
        } else if (prescription.getStatus() == PrescriptionStatus.ACCEPT) { // for when our patient has accepting prescription from admin
            int numberOFNotExist = 0;
            while (true) {
                loadPatientPrescription(patient);
                Receipt receipt = loadPatientReceipt(patient);
                for (int i = 0; i < receipt.getDrugs().size(); i++) {
                    int myCount = receipt.getDrugs().getItem(i).getCount();
                    int patientCount = patient.getPrescription().getDrugs().getItem(i).getCount();
                    if (myCount == 0) {
                        System.out.println((i + 1) + ") We dont have a drug with " + receipt.getDrugs().getItem(i).getName() + "'s name");
                        numberOFNotExist++;
                    } else {
                        if (myCount < patientCount) {
                            showInformationAboutDrug((i + 1) + ") For " + receipt.getDrugs().getItem(i).getName() +
                                    " drug, you want " + patientCount + ", But we have " + myCount + " number.", receipt, i);
                        } else {
                            showInformationAboutDrug((i + 1) + ") For " + receipt.getDrugs().getItem(i).getName() +
                                    " drug, We have enough number.", receipt, i);
                        }
                    }
                }
                if(numberOFNotExist == receipt.getDrugs().size()){
                    System.out.println("Your prescription was canceled");
                    cancelPrescription(prescription, receipt);
                    Check.pressEnter();
                    break;
                }
                System.out.println("Total price is: " + receipt.getPrice() + " Toman");
                System.out.print("If you want to delete something, Enter the id otherwise Enter 0: ");
                System.out.print("If you want to delete your receipt, Enter delete: ");
                String command = scanner.next();
                if(Objects.equals(command, "delete")){
                    cancelPrescription(prescription, receipt);
                    break;
                }
                int inputId = check.checkCorrectInt(command);
                if (inputId == 0) {
                    paidReceipt(prescription, receipt);
                    break;
                } else {
                    int index = check.checkIntegerRange(inputId, receipt.getDrugs().size());
                    if(index == -1){
                        System.out.println("Wrong id");
                        return;
                    }
                    removeDrugFromReceipt(receipt, index);
                }

            }
        }
    }

    private void cancelPrescription(Prescription prescription, Receipt receipt) {
        receipt.setReceiptStatus(ReceiptStatus.CANCEL);
        receipt.setPrice(0);
        prescription.setStatus(PrescriptionStatus.CANCEL);
        patientService.changeReceiptStatus(receipt);
        patientService.changePrescriptionStatus(prescription);
    }

    private void removeDrugFromReceipt(Receipt receipt, int inputId) {
        patientService.removeDrugFromReceipt(receipt.getDrugs().getItem(inputId - 1).getId());
        // add drug to database
        patientService.changeNumberOfDrug(receipt.getDrugs().getItem(inputId - 1).getCount(),
                receipt.getDrugs().getItem(inputId - 1).getName());
    }

    private void paidReceipt(Prescription prescription, Receipt receipt) {
        receipt.setReceiptStatus(ReceiptStatus.PAID);
        patientService.changeReceiptStatus(receipt);
        patientService.changePrescriptionStatus(prescription);
    }

    private void addPrescriptionForPatient(Patient patient) {
        patient.setPrescription(new Prescription(patient.getId()));
        patientService.addPrescription(patient);
        // for set prescription id
        patient.setPrescription(patientService.loadPrescription(patient));
    }

    private Receipt loadPatientReceipt(Patient patient) {
        return patientService.loadReceipt(patient.getId());
    }

    private void loadPatientPrescription(Patient patient) {
        patient.setPrescription(patientService.loadPrescription(patient));
        patient.getPrescription().setDrugs(new MyList<>());
        patient.getPrescription().getDrugs().setItems(patientService.
                loadPrescriptionsDrugs(patient.getPrescription().getId()));
    }

    private void showInformationAboutDrug(String i, Receipt receipt, int i1) {
        System.out.print(i);
        System.out.println(" Price is: " + receipt.getDrugs().getItem(i1).getPrice() + " Toman");
        receipt.setPrice(receipt.getPrice() + receipt.getDrugs().getItem(i1).getPrice());
    }

}
