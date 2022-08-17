import repository.drug.DrugRepositoryImpl;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;
import repository.receipt.ReceiptRepositoryImpl;
import service.drug.DrugServiceImpl;
import service.patient.PatientServiceImpl;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;
import view.MainMenu;

public class Main {

    public static void main(String[] args) {
        MainMenu menu = new MainMenu(new PatientServiceImpl(
                new PatientRepositoryImpl(),
                new PrescriptionServiceImpl(new PrescriptionRepositoryImpl()),
                new ReceiptServiceImpl(new ReceiptRepositoryImpl()),
                new DrugServiceImpl(new DrugRepositoryImpl())
        ));
        menu.showMenu();

    }

}
