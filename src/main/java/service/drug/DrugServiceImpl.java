package service.drug;

import entity.Drug;
import repository.drug.DrugRepositoryImpl;

public class DrugServiceImpl {

    private final DrugRepositoryImpl drugRepository = new DrugRepositoryImpl();

    public void increaseNumberOfDrug(int count, String name) {
        drugRepository.increaseNumberOfDrug(count, name);
    }

    public Drug load(String name){
        return drugRepository.load(name);
    }

    public void update(Drug drug){
        drugRepository.update(drug);
    }

}
