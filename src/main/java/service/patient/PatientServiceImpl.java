package service.patient;

import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.patient.interfaces.PatientRepository;
import service.drug.interfaces.DrugService;
import service.patient.interfaces.PatientService;
import service.prescription.interfaces.PrescriptionService;
import service.receipt.interfaces.ReceiptService;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PrescriptionService prescriptionService;
    private final ReceiptService receiptService;
    private final DrugService drugService;

    public PatientServiceImpl(PatientRepository patientRepository, PrescriptionService prescriptionService,
                              ReceiptService receiptService, DrugService drugService) {
        this.patientRepository = patientRepository;
        this.prescriptionService = prescriptionService;
        this.receiptService = receiptService;
        this.drugService = drugService;
    }

    @Override
    public Patient load(Patient patient) {
        if (patientRepository.load(patient.getNationalCode()) == null) {
            patientRepository.save(patient);
        }
        return patientRepository.load(patient.getNationalCode());
    }

    @Override
    public Prescription loadPrescription(Patient patient) {
        return prescriptionService.loadPatientPrescription(patient.getId());
    }

    @Override
    public void addDrug(Patient patient, SimpleDrug drug) {
        patient.getPrescription().getDrugs().add(drug);
        prescriptionService.saveDrug(patient.getPrescription().getId(), drug);
    }

    @Override
    public void changePrescriptionStatus(Prescription prescription) {
        if (prescription.getStatus() == PrescriptionStatus.ACCEPT) {
            prescription.setStatus(PrescriptionStatus.CONFIRM);
            updatePrescription(prescription);
        } else {
            prescription.setStatus(PrescriptionStatus.CANCEL);
            updatePrescription(prescription);
        }
    }

    @Override
    public void addPrescription(Patient patient) {
        prescriptionService.save(patient.getPrescription());
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        prescriptionService.update(prescription);
    }

    @Override
    public Receipt loadReceipt(long id) {
        return receiptService.loadPatientReceipt(id);
    }

    @Override
    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        return prescriptionService.loadPrescriptionsDrugs(id);
    }

    @Override
    public void removeDrugFromReceipt(long id) {
        receiptService.removeDrugFromReceipt(id);
    }

    @Override
    public void changeNumberOfDrug(int count, String name) {
        drugService.increaseNumberOfDrug(count, name);
    }

    @Override
    public void changeReceiptStatus(Receipt receipt) {
        receiptService.update(receipt);
    }

}
