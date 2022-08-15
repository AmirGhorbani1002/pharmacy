package service.prescription;

import entity.Prescription;
import entity.enums.PrescriptionStatus;
import repository.prescription.PrescriptionRepositoryImpl;

public class PrescriptionServiceImpl {

    private final PrescriptionRepositoryImpl prescriptionRepository = new PrescriptionRepositoryImpl();

    public void updateStatus(long id, PrescriptionStatus status){
        prescriptionRepository.update(id,status);
    }

}
