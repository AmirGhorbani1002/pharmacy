package util.list.drug;

import entity.Drug;
import entity.SimpleDrug;
import util.list.MyList;

import java.util.Arrays;

public class DrugListImpl implements MyList<SimpleDrug> {

    private SimpleDrug[] drugs = new SimpleDrug[1000];
    private int index = 0;

    @Override
    public void add(SimpleDrug drug) {
        if (index > drugs.length - 1) {
            drugs = Arrays.copyOf(drugs, index * 2);
        }
        drugs[index] = drug;
        index++;
    }

    @Override
    public void remove(int id) {
        drugs[id] = null;
        if (index - id >= 0) System.arraycopy(drugs, id + 1, drugs, id, index - id);
    }

    public SimpleDrug[] getDrugs() {
        return drugs;
    }

    public void setDrugs(SimpleDrug[] drugs) {
        this.drugs = drugs;
    }

    public int getIndex() {
        return index;
    }

}
