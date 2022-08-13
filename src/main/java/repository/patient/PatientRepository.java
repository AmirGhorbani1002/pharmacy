package repository.patient;

import entity.Patient;

public interface PatientRepository {

    void save(Patient patient);
    Patient load(String nationalCode);

}
