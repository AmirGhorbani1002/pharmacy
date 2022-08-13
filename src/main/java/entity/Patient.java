package entity;

import entity.abstracts.Person;
import util.list.MyList;
import util.list.drug.DrugListImpl;

public class Patient extends Person {

    private final MyList<SimpleDrug> drugs = new DrugListImpl();
    public Patient(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public MyList<SimpleDrug> getDrugs() {
        return drugs;
    }
}
