package repository.prescription.interfaces;

import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;

public interface PrescriptionRepository {

    void save(Prescription prescription);
    void remove(long id);
    Prescription load(long id);
    Prescription[] loadAllPendingPrescription();
    Prescription loadPatientPrescription(long id);
    SimpleDrug[] loadPrescriptionsDrugs(long id);
    void update(Prescription prescription);
    void update(long id, PrescriptionStatus status);
    void saveDrug(long id, SimpleDrug drug);
}
