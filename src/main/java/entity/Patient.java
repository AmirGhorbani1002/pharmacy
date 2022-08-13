package entity;

import entity.abstracts.Person;
import util.list.MyList;
import util.list.drug.DrugListImpl;

public class Patient extends Person {

    private Prescription prescription;
    public Patient(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
