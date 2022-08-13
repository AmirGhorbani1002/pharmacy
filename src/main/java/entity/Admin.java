package entity;

import entity.abstracts.Person;
import util.list.MyList;
import util.list.patient.PatientListImpl;

public class Admin extends Person {

    private final MyList<Patient> patients = new PatientListImpl();

    public Admin(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public MyList<Patient> getPatients() {
        return patients;
    }
}
