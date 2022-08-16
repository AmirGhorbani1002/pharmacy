package service.patient;

import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;

public class PatientServiceImpl {

    private final PatientRepositoryImpl patientRepository = new PatientRepositoryImpl();
    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    public Patient load(Patient patient) {
        if (patientRepository.load(patient.getNationalCode()) == null) {
            patientRepository.save(patient);
        }
        return patientRepository.load(patient.getNationalCode());
    }

    public Prescription loadPrescription(Patient patient) {
        return patientRepository.loadPrescription(patient.getId());
    }

    public void addDrug(Patient patient, SimpleDrug drug) {
        patient.getPrescription().getDrugs().add(drug);
        prescriptionRepository.saveDrug(patient.getPrescription().getId(), drug);
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
        prescriptionRepository.save(patient.getPrescription());
    }

    public void updatePrescription(Prescription prescription){
        prescriptionRepository.update(prescription);
    }

    public Receipt loadReceipt(long id) {
        return patientRepository.loadReceipt(id);
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        return patientRepository.loadPrescriptionsDrugs(id);
    }

    public void removeDrugFromReceipt(long id) {
        patientRepository.removeDrugFromReceipt(id);
    }

    public void changeNumberOfDrug(int count, String name) {
        patientRepository.changeNumberOfDrug(count, name);
    }

    public void changeReceiptStatus(Receipt receipt){
        patientRepository.changeReceipt(receipt);
    }

    private void addPrescription(Patient patient, PrescriptionStatus confirm) {
        patient.getPrescription().setStatus(confirm);
        prescriptionRepository.save(patient.getPrescription());
    }

}
