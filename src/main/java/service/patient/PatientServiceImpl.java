package service.patient;

import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.patient.PatientRepositoryImpl;
import service.drug.DrugServiceImpl;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;

public class PatientServiceImpl {

    private final PatientRepositoryImpl patientRepository = new PatientRepositoryImpl();
    private final PrescriptionServiceImpl prescriptionService = new PrescriptionServiceImpl();
    private final ReceiptServiceImpl receiptService = new ReceiptServiceImpl();
    private final DrugServiceImpl drugService = new DrugServiceImpl();

    public Patient load(Patient patient) {
        if (patientRepository.load(patient.getNationalCode()) == null) {
            patientRepository.save(patient);
        }
        return patientRepository.load(patient.getNationalCode());
    }

    public Prescription loadPrescription(Patient patient) {
        return prescriptionService.loadPatientPrescription(patient.getId());
    }

    public void addDrug(Patient patient, SimpleDrug drug) {
        patient.getPrescription().getDrugs().add(drug);
        prescriptionService.saveDrug(patient.getPrescription().getId(), drug);
    }

    public void prescriptionStatus(Prescription prescription) {
        if (prescription.getStatus() == PrescriptionStatus.ACCEPT) {
            prescription.setStatus(PrescriptionStatus.CONFIRM);
            updatePrescription(prescription);
        } else {
            prescription.setStatus(PrescriptionStatus.CANCEL);
            updatePrescription(prescription);
        }
    }

    public void addPrescription(Patient patient) {
        prescriptionService.save(patient.getPrescription());
    }

    public void updatePrescription(Prescription prescription) {
        prescriptionService.update(prescription);
    }

    public Receipt loadReceipt(long id) {
        return receiptService.loadPatientReceipt(id);
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        return prescriptionService.loadPrescriptionsDrugs(id);
    }

    public void removeDrugFromReceipt(long id) {
        receiptService.removeDrugFromReceipt(id);
    }

    public void changeNumberOfDrug(int count, String name) {
        drugService.increaseNumberOfDrug(count, name);
    }

    public void changeReceiptStatus(Receipt receipt) {
        receiptService.update(receipt);
    }

}
