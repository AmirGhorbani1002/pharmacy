package service.prescription;

import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.prescription.PrescriptionRepositoryImpl;
import util.list.MyList;

public class PrescriptionServiceImpl {

    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    public void save(Prescription prescription){
        prescriptionRepository.save(prescription);
    }

    public void update(Prescription prescription){
        prescriptionRepository.update(prescription);
    }

    public void updateStatus(long id, PrescriptionStatus status) {
        prescriptionRepository.update(id, status);
    }

    public void loadAllPendingPrescription() {
        MyList<Prescription> prescriptions = new MyList<>();
        prescriptions.setItems(prescriptionRepository.loadAllPendingPrescription());
        System.out.println(prescriptions);
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        return prescriptionRepository.loadPrescriptionsDrugs(id);
    }

    public Prescription loadPatientPrescription(long id) {
        return prescriptionRepository.loadPatientPrescription(id);
    }

    public void saveDrug(long id, SimpleDrug drug) {
        prescriptionRepository.saveDrug(id, drug);
    }

}
