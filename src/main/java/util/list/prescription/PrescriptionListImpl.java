package util.list.prescription;

import entity.Prescription;
import util.list.MyList;

import java.util.Arrays;

public class PrescriptionListImpl implements MyList<Prescription> {

    private Prescription[] prescriptions = new Prescription[1000];
    private int index = 0;

    @Override
    public void add(Prescription patient) {
        if (index > prescriptions.length - 1) {
            prescriptions = Arrays.copyOf(prescriptions, index * 2);
        }
        prescriptions[index] = patient;
        index++;
    }

    @Override
    public void remove(int id) {
        prescriptions[id] = null;
        if (index - id >= 0) System.arraycopy(prescriptions, id + 1, prescriptions, id, index - id);
    }
}
