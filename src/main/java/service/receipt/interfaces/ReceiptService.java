package service.receipt.interfaces;

import entity.Drug;
import entity.Receipt;

public interface ReceiptService {

    void save(Receipt receipt);
    Receipt load(long id);
    void saveDrug(long id, Drug drug, int number);
    Receipt loadPatientReceipt(long id);
    void removeDrugFromReceipt(long id);
    void update(Receipt receipt);
}
