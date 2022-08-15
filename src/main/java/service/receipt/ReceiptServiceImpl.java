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

}
