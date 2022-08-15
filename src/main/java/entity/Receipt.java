package entity;

import entity.Drug;
import util.list.MyList;

public class Receipt {
    private long id;
    private final MyList<Drug> drugs = new MyList<Drug>();
    private long prescriptionId;
    private float price;

    public Receipt(long id, long prescriptionId, float price) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MyList<Drug> getDrugs() {
        return drugs;
    }
}
