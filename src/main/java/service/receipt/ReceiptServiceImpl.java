package service.receipt;

import entity.Drug;
import entity.Receipt;
import repository.receipt.ReceiptRepositoryImpl;

public class ReceiptServiceImpl {

    private final ReceiptRepositoryImpl receiptRepository = new ReceiptRepositoryImpl();

    public void save(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    public Receipt load(long id) {
        return receiptRepository.load(id);
    }

    public void saveDrug(long id, Drug drug, int number) {
        receiptRepository.saveDrug(id, drug, number);
    }

    public Receipt loadPatientReceipt(long id){
        return receiptRepository.loadPatientReceipt(id);
    }

    public void removeDrugFromReceipt(long id){
        receiptRepository.removeDrugFromReceipt(id);
    }

    public void update(Receipt receipt){
        receiptRepository.update(receipt);
    }

}
