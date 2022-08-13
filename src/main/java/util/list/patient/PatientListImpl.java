package util.list.patient;

import entity.Patient;
import util.list.MyList;

import java.util.Arrays;

public class PatientListImpl implements MyList<Patient> {

    private Patient[] patients = new Patient[1000];
    private int index = 0;

    @Override
    public void add(Patient patient) {
        if (index > patients.length - 1) {
            patients = Arrays.copyOf(patients, index * 2);
        }
        patients[index] = patient;
        index++;
    }

    @Override
    public void remove(int id) {
        patients[id] = null;
        if (index - id >= 0) System.arraycopy(patients, id + 1, patients, id, index - id);
    }
}
