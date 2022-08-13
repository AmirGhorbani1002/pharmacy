package service.patient;

import entity.Patient;
import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.BaseRepository;
import repository.patient.PatientRepository;

public class PatientServiceImpl {

    private final PatientRepository patientRepository;
    private final BaseRepository<Prescription> prescriptionRepository;

    public PatientServiceImpl(PatientRepository patientRepository, BaseRepository<Prescription> prescriptionRepository) {
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public Patient load(Patient patient){
        if(patientRepository.load(patient.getNationalCode()) == null){
            patientRepository.save(patient);
        }
        return patientRepository.load(patient.getNationalCode());
    }

    public void addDrug(Patient patient, SimpleDrug drug){
        patient.getPrescription().getDrugs().add(drug);
    }

    public void prescriptionStatus(Patient patient){
        if(patient.getPrescription().getStatus() == PrescriptionStatus.ACCEPT){
            addPrescription(patient, PrescriptionStatus.CONFIRM);
        } else{
            addPrescription(patient, PrescriptionStatus.CANCEL);
        }
    }

    private void addPrescription(Patient patient, PrescriptionStatus confirm) {
        patient.getPrescription().setStatus(confirm);
        prescriptionRepository.save(patient.getPrescription());
    }

}
