package service.drug;

import entity.Drug;
import repository.drug.interfaces.DrugRepository;
import service.drug.interfaces.DrugService;

public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    public DrugServiceImpl(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

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

    @Override
    public void save(Drug drug) {
        drugRepository.save(drug);
    }

}
