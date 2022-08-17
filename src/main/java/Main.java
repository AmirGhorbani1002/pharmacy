import repository.admin.AdminRepositoryImpl;
import repository.drug.DrugRepositoryImpl;
import repository.patient.PatientRepositoryImpl;
import repository.prescription.PrescriptionRepositoryImpl;
import repository.receipt.ReceiptRepositoryImpl;
import service.admin.AdminServiceImpl;
import service.drug.DrugServiceImpl;
import service.patient.PatientServiceImpl;
import service.prescription.PrescriptionServiceImpl;
import service.receipt.ReceiptServiceImpl;
import view.main.MainMenu;

public class Main {

    public static void main(String[] args) {
        MainMenu menu = new MainMenu(new PatientServiceImpl(
                new PatientRepositoryImpl(),
                new PrescriptionServiceImpl(new PrescriptionRepositoryImpl()),
                new ReceiptServiceImpl(new ReceiptRepositoryImpl()),
                new DrugServiceImpl(new DrugRepositoryImpl())
        ), new AdminServiceImpl(new DrugServiceImpl(new DrugRepositoryImpl()),
                new ReceiptServiceImpl(new ReceiptRepositoryImpl()),
                new PrescriptionServiceImpl(new PrescriptionRepositoryImpl()),
                new AdminRepositoryImpl()));
        menu.showMenu();

    }

}
