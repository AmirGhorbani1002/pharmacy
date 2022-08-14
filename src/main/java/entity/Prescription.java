package entity;

import entity.enums.PrescriptionStatus;
import util.list.MyList;

public class Prescription {

    private long id;
    private final long patientId;
    private final MyList<SimpleDrug> drugs = new MyList<>();
    private PrescriptionStatus status;

    public Prescription(long patientId) {
        this.patientId = patientId;
        status = PrescriptionStatus.PENDING;
    }

    public Prescription(long patientId, PrescriptionStatus status) {
        this.patientId = patientId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
