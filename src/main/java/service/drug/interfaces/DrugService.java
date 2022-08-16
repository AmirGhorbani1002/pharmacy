package service.drug.interfaces;

import entity.Drug;

public interface DrugService {

    void increaseNumberOfDrug(int count, String name);
    Drug load(String name);
    void update(Drug drug);
}
