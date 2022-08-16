package service.admin.interfaces;

public interface AdminService {

    void loadAllPendingPrescription();
    void loadPrescription(long id);
    int loadPrescriptionsDrugs(long id, int index);
    void approvalOfTheDrug(long id, int index, int number);
    int numberOfDrugs();
    void confirmPrescription(long id);
}
