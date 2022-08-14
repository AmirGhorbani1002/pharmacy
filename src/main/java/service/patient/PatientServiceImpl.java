package service.patient;

import entity.Patient;
import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;

public class PatientServiceImpl {

    private final PatientRepositoryImpl patientRepository = new PatientRepositoryImpl();
    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    public Patient load(Patient patient){
        if(patientRepository.load(patient.getNationalCode()) == null){
            patientRepository.save(patient);
        }
        return patientRepository.load(patient.getNationalCode());
    }

    public Prescription loadPrescription(Patient patient){
        return patientRepository.loadPrescription(patient.getId());
    }

    public void addDrug(Patient patient, SimpleDrug drug){
        patient.getPrescription().getDrugs().add(drug);
        prescriptionRepository.saveDrug(patient.getId(),drug);
    }

    public void prescriptionStatus(Patient patient){
        if(patient.getPrescription().getStatus() == PrescriptionStatus.ACCEPT){
            addPrescription(patient, PrescriptionStatus.CONFIRM);
        } else{
            addPrescription(patient, PrescriptionStatus.CANCEL);
        }
    }

    public void addPrescription(Patient patient){
        prescriptionRepository.save(patient.getPrescription());
    }

    private void addPrescription(Patient patient, PrescriptionStatus confirm) {
        patient.getPrescription().setStatus(confirm);
        prescriptionRepository.save(patient.getPrescription());
    }

}
