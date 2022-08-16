package service.prescription;

import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.prescription.PrescriptionRepositoryImpl;
import util.list.MyList;

public class PrescriptionServiceImpl {

    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    public void updateStatus(long id, PrescriptionStatus status){
        prescriptionRepository.update(id,status);
    }

    public void loadAllPendingPrescription(){
        MyList<Prescription> prescriptions = new MyList<>();
        prescriptions.setItems(prescriptionRepository.loadAllPendingPrescription());
        System.out.println(prescriptions);
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id){
        return prescriptionRepository.loadPrescriptionsDrugs(id);
    }

}
