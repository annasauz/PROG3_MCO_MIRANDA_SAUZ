import java.util.Scanner;

public class SpecialVendingMachineController extends RegularVendingMachineController {

    private TextInterface textInterface;
    private SpecialVendingMachine specialVendingMachine;
    private Scanner scanner;

    /**
     * Initializes a Special Vending Machine Controller.
     * Inherited from VendingMachineController.
     */
    public SpecialVendingMachineController(TextInterface textInterface, SpecialVendingMachine specialVendingMachine, Scanner scanner) {
        super(textInterface, specialVendingMachine, scanner);
    }

}
