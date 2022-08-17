package service.prescription;

import check.Check;
import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.prescription.PrescriptionRepositoryImpl;
import service.prescription.interfaces.PrescriptionService;
import util.list.MyList;

public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    @Override
    public void save(Prescription prescription){
        prescriptionRepository.save(prescription);
    }

    @Override
    public void update(Prescription prescription){
        prescriptionRepository.update(prescription);
    }

    @Override
    public void updateStatus(long id, PrescriptionStatus status) {
        prescriptionRepository.update(id, status);
    }

    @Override
    public void loadAllPendingPrescription() {
        MyList<Prescription> prescriptions = new MyList<>();
        prescriptions.setItems(prescriptionRepository.loadAllPendingPrescription());
        if(prescriptions.size() == 0){
            System.out.println("No prescription for checking");
            return;
        }
        System.out.println(prescriptions);
    }

    @Override
    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        return prescriptionRepository.loadPrescriptionsDrugs(id);
    }

    @Override
    public Prescription loadPatientPrescription(long id) {
        return prescriptionRepository.loadPatientPrescription(id);
    }

    @Override
    public void saveDrug(long id, SimpleDrug drug) {
        prescriptionRepository.saveDrug(id, drug);
    }

    @Override
    public Prescription load(long id){
        return prescriptionRepository.load(id);
    }

}
