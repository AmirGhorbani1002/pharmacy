package entity;

import entity.enums.PrescriptionStatus;
import util.list.MyList;
import util.list.drug.DrugListImpl;

public class Prescription {

    private final long id;
    private final long patientId;
    private final MyList<SimpleDrug> drugs = new DrugListImpl();
    private PrescriptionStatus status;

    public Prescription(long id, long patientId) {
        this.id = id;
        this.patientId = patientId;
        status = PrescriptionStatus.PENDING;
    }

    public long getId() {
        return id;
    }

    public long getPatientId() {
        return patientId;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public MyList<SimpleDrug> getDrugs() {
        return drugs;
    }
}
