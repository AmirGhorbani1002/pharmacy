package service.admin;

import entity.*;
import repository.admin.AdminRepositoryImpl;
import repository.drug.DrugRepositoryImpl;
import util.list.MyList;

public class AdminServiceImpl {

    private final DrugRepositoryImpl drugRepository = new DrugRepositoryImpl();
    private final AdminRepositoryImpl adminRepository = new AdminRepositoryImpl();
    private final MyList<Prescription> prescriptions = new MyList<>();
    private final MyList<SimpleDrug> simpleDrugs = new MyList<>();
    private final MyList<Drug> drugs = new MyList<>();
    private Receipt receipt;

    public void loadAllPrescriptions(Admin admin) {
        prescriptions.setItems(adminRepository.loadAllPrescriptions());
        System.out.println(prescriptions);
    }

    public int loadPrescriptionsDrugs(int id, int index) {
        if(simpleDrugs.getItem(0) == null)
            simpleDrugs.setItems(adminRepository.loadPrescriptionsDrugs(id));
        SimpleDrug simpleDrug = simpleDrugs.getItem(index);
        Drug drug = drugRepository.load(simpleDrug.getName());
        drugs.add(drug);
        System.out.println("The patient wants " + simpleDrug.getCount() +
                " drug of " + simpleDrug.getName());
        if (drug == null) {
            return -1;
        } else {
            if (drug.getCount() < simpleDrug.getCount()) {
                System.out.println("We do not have this number of drug in our warehouse. We have "
                        + drug.getCount() + " but patient wants " + simpleDrug.getCount());
                return 0;
            } else {
                System.out.println("There are enough of these drug in stock. We have "
                        + drug.getCount() + " but patient wants " + simpleDrug.getCount());
                return 1;
            }
        }
    }

    public void approvalOfTheDrug(int id, int index, int number) {
        if(receipt == null)
            receipt = new Receipt(-1, id, 0);
        receipt.getDrugs().add(drugs.getItem(index));
        receipt.setPrice(receipt.getPrice() + drugs.getItem(index).getPrice() * number);
    }

    public int numberOfDrugs(int id) {
        simpleDrugs.setItems(adminRepository.loadPrescriptionsDrugs(id));
        return simpleDrugs.size();
    }

    public void confirmReceipt(){

    }

}
