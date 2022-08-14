package service.admin;

import entity.Admin;
import entity.Drug;
import entity.Prescription;
import repository.drug.DrugRepositoryImpl;

public class AdminServiceImpl {

    private DrugRepositoryImpl drugRepository;

    public void addPrescription(Admin admin, Prescription prescription){
        admin.getPrescriptions().add(prescription);
    }

    public void loadAllPrescription(Admin admin){
        for(Prescription prescription : admin.getPrescriptions().getList()){
            System.out.println(prescription);
        }
    }

    public void loadPrescription(Admin admin, int id){
            System.out.println(admin.getPrescriptions().getList()[id]);
    }

    public Drug loadDrug(Admin admin, int prescriptionIndex, int drugIndex){
        return drugRepository.load(admin.getPrescriptions().getList()[prescriptionIndex]
                .getDrugs().getList()[drugIndex].getName());
    }

}
