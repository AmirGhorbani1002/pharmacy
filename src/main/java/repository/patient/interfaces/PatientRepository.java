package repository.patient.interfaces;

import entity.Patient;

public interface PatientRepository {

    void save(Patient patient);
    Patient load(String nationalCode);

}
