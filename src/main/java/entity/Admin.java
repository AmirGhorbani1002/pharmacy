package entity;

import entity.abstracts.Person;
import util.list.MyList;

public class Admin extends Person {

    private final MyList<Prescription> prescriptions = new MyList<>();

    public Admin(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public MyList<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
