package service.drug;

import entity.Drug;
import repository.drug.DrugRepositoryImpl;
import service.drug.interfaces.DrugService;

public class DrugServiceImpl implements DrugService {

    private final DrugRepositoryImpl drugRepository = new DrugRepositoryImpl();

    @Override
    public void increaseNumberOfDrug(int count, String name) {
        drugRepository.increaseNumberOfDrug(count, name);
    }

    @Override
    public Drug load(String name){
        return drugRepository.load(name);
    }

    @Override
    public void update(Drug drug){
        drugRepository.update(drug);
    }

}
