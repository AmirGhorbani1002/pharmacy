package service.drug;

import repository.drug.DrugRepositoryImpl;

public class DrugServiceImpl {

    private final DrugRepositoryImpl drugRepository = new DrugRepositoryImpl();

    public void increaseNumberOfDrug(int count, String name) {
        drugRepository.increaseNumberOfDrug(count, name);
    }

}
