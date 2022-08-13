package entity;

import entity.abstracts.Person;
import util.list.MyList;
import util.list.prescription.PrescriptionListImpl;

public class Admin extends Person {

    private final MyList<Prescription> prescriptions = new PrescriptionListImpl();

    public Admin(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public MyList<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
