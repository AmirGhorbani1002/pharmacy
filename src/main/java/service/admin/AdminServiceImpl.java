package service.admin;

import entity.*;
import entity.enums.PrescriptionStatus;
import entity.enums.ReceiptStatus;
import repository.admin.AdminRepositoryImpl;
import repository.drug.DrugRepositoryImpl;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;
import util.list.MyList;

public class AdminServiceImpl {

    private final DrugRepositoryImpl drugRepository = new DrugRepositoryImpl();
    private final ReceiptServiceImpl receiptService = new ReceiptServiceImpl();
    private final PrescriptionServiceImpl prescriptionService = new PrescriptionServiceImpl();
    private final AdminRepositoryImpl adminRepository = new AdminRepositoryImpl();
    private final MyList<SimpleDrug> simpleDrugs = new MyList<>();
    private final MyList<Drug> drugs = new MyList<>();

    public void loadAllPrescriptions(Admin admin) {
        MyList<Prescription> prescriptions = new MyList<>();
        prescriptions.setItems(adminRepository.loadAllPrescriptions());
        System.out.println(prescriptions);
    }

    public void loadPrescription(long id){
        simpleDrugs.setItems(adminRepository.loadPrescriptionsDrugs(id));
    }

    public int loadPrescriptionsDrugs(long id, int index) {
        SimpleDrug simpleDrug = simpleDrugs.getItem(index);
        Drug drug = drugRepository.load(simpleDrug.getName());
        drugs.add(drug);
        System.out.println("The patient wants " + simpleDrug.getCount() +
                " drug of " + simpleDrug.getName());
        if (drug == null) {
            Drug temp = new Drug(-1, simpleDrug.getName(), 0, 0);
            receiptService.saveDrug(id, temp, 0);
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

    public void approvalOfTheDrug(long id, int index, int number) {
        Receipt receipt = receiptService.load(id);
        if (receipt == null) {
            receipt = new Receipt(-1, id, 0);
            receipt.setReceiptStatus(ReceiptStatus.UNPAID);
            receiptService.save(receipt);
        }
        receiptService.saveDrug(id, drugs.getItem(index), number);
    }

    public int numberOfDrugs() {
        System.out.println(simpleDrugs.size());
        return simpleDrugs.size();
    }

    public void confirmPrescription(long id) {
        prescriptionService.updateStatus(id, PrescriptionStatus.ACCEPT);
    }

}
