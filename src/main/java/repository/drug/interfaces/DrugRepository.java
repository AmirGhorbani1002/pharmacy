package repository.drug.interfaces;

import entity.Drug;

public interface DrugRepository {

    void save(Drug drug);
    void remove(long id);
    Drug load(long id);
    Drug load(String name);
    void update(Drug drug);
    void increaseNumberOfDrug(int count, String name);
}
