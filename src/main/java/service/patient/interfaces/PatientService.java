package service.patient.interfaces;

import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;

public interface PatientService {

    Patient load(Patient patient);
    Prescription loadPrescription(Patient patient);
    void addDrug(Patient patient, SimpleDrug drug);
    void changePrescriptionStatus(Prescription prescription);
    void addPrescription(Patient patient);
    void updatePrescription(Prescription prescription);
    Receipt loadReceipt(long id);
    SimpleDrug[] loadPrescriptionsDrugs(long id);
    void removeDrugFromReceipt(long id);
    void changeNumberOfDrug(int count, String name);
    void changeReceiptStatus(Receipt receipt);
}
