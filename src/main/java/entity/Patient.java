package entity;

import entity.abstracts.Person;

public class Patient extends Person {

    private long id;
    private Prescription prescription;
    private Receipt receipt;
    public Patient(String firstname, String lastname, String nationalCode) {
        super(firstname, lastname, nationalCode);
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
