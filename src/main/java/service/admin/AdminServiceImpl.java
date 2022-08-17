package service.admin;

import entity.*;
import entity.enums.PrescriptionStatus;
import entity.enums.ReceiptStatus;
import repository.admin.interfaces.AdminRepository;
import service.admin.interfaces.AdminService;
import service.drug.interfaces.DrugService;
import service.prescription.interfaces.PrescriptionService;
import service.receipt.interfaces.ReceiptService;
import util.list.MyList;

public class AdminServiceImpl implements AdminService {

    private final DrugService drugService;
    private final ReceiptService receiptService;
    private final PrescriptionService prescriptionService;
    private final AdminRepository adminRepository;

    public AdminServiceImpl(DrugService drugService, ReceiptService receiptService, PrescriptionService prescriptionService, AdminRepository adminRepository) {
        this.drugService = drugService;
        this.receiptService = receiptService;
        this.prescriptionService = prescriptionService;
        this.adminRepository = adminRepository;
    }

    private final MyList<SimpleDrug> simpleDrugs = new MyList<>();
    private final MyList<Drug> drugs = new MyList<>();

    @Override
    public boolean load(String password) {
        return adminRepository.load(password);
    }

    @Override
    public void loadAllPendingPrescription() {
        prescriptionService.loadAllPendingPrescription();
    }

    @Override
    public boolean loadPrescription(long id) {
        simpleDrugs.setItems(prescriptionService.loadPrescriptionsDrugs(id));
        if(simpleDrugs.getItem(0) == null)
            return false;
        return prescriptionService.load(id).getStatus() == PrescriptionStatus.PENDING;
    }

    @Override
    public int[] loadPrescriptionsDrugs(long id, int index) {
        SimpleDrug simpleDrug = simpleDrugs.getItem(index);
        Drug drug = drugService.load(simpleDrug.getName());
        drugs.add(drug);
        System.out.println("The patient wants " + simpleDrug.getCount() +
                " drug of " + simpleDrug.getName());
        if (drug == null) {
            Drug temp = new Drug(-1, simpleDrug.getName(), 0, 0);
            Receipt receipt = receiptService.load(id);
            if (receipt == null){
                receiptService.save(new Receipt(-1, id, 0));
                receipt = receiptService.load(id);
            }
            receiptService.saveDrug(receipt.getId(), temp, 0);
            return new int[]{-1, -1};
        } else {
            if (drug.getCount() < simpleDrug.getCount()) {
                System.out.println("We do not have this number of drug in our warehouse. We have "
                        + drug.getCount() + " but patient wants " + simpleDrug.getCount());
                return new int[]{0, drug.getCount()};
            } else {
                System.out.println("There are enough of these drug in stock. We have "
                        + drug.getCount() + " and patient wants " + simpleDrug.getCount());
                return new int[]{1, drug.getCount()};
            }
        }
    }

    @Override
    public void approvalOfTheDrug(long id, int index, int number) {
        Receipt receipt = receiptService.load(id);
        if (receipt == null) {
            receipt = new Receipt(-1, id, 0);
            receipt.setReceiptStatus(ReceiptStatus.UNPAID);
            receiptService.save(receipt);
        }
        Drug drug = drugs.getItem(index);
        drug.setCount(drug.getCount() - number);
        drugService.update(drug);
        receiptService.saveDrug(receipt.getId(), drugs.getItem(index), number);
    }

    @Override
    public int numberOfDrugs() {
        return simpleDrugs.size();
    }

    @Override
    public void confirmPrescription(long id) {
        prescriptionService.updateStatus(id, PrescriptionStatus.ACCEPT);
    }

    @Override
    public void addDrug(Drug drug) {
        Drug drug2 = drugService.load(drug.getName());
        if (drug2 == null)
            drugService.save(drug);
        else {
            drug2.setCount(drug.getCount() + drug2.getCount());
            drug2.setPrice(drug.getPrice());
            drugService.update(drug2);
        }

    }

}
