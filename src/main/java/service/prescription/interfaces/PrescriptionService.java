package service.prescription.interfaces;

import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;

public interface PrescriptionService {

    void save(Prescription prescription);
    void update(Prescription prescription);
    void updateStatus(long id, PrescriptionStatus status);
    void loadAllPendingPrescription();
    SimpleDrug[] loadPrescriptionsDrugs(long id);
    Prescription loadPatientPrescription(long id);
    void saveDrug(long id, SimpleDrug drug);
}
