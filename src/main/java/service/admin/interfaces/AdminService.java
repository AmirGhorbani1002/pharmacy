package service.admin.interfaces;

import entity.Drug;
import entity.Prescription;

public interface AdminService {

    boolean load(String password);
    Prescription[] loadAllPendingPrescription();
    boolean loadPrescription(long id);
    int[] loadPrescriptionsDrugs(long id, int index);
    void approvalOfTheDrug(long id, int index, int number);
    int numberOfDrugs();
    void confirmPrescription(long id);

    void addDrug(Drug drug);
}
