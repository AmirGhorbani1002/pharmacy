package repository.receipt.interfaces;

import entity.Drug;
import entity.Receipt;

public interface ReceiptRepository {

    void save(Receipt receipt);
    Receipt load(long id);
    Receipt loadPatientReceipt(long id);
    void removeDrugFromReceipt(long id);
    void saveDrug(long id, Drug drug, int number);
    void update(Receipt receipt);
}
