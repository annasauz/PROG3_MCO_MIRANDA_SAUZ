import java.util.Scanner;

public class VendingMachineSoftwareLoop {
    private TextInterface textInterface;
    private RegularVendingMachine vendingMachine;
    private boolean isRunning;
    private Scanner scanner = new Scanner(System.in);

    // Constructor
    VendingMachineSoftwareLoop(TextInterface textInterface) {
        this.textInterface = textInterface;
        this.vendingMachine = null;
        this.isRunning = true;
    }

    // Methods
    public void run() {
        while (isRunning) {
            textInterface.printCreateAndTest();
            int choice = scanner.nextInt();

            switch (choice){
                case 1:
                    this.vendingMachine = new RegularVendingMachine();
                    System.out.println("Vending machine Created.");
                    break;
                case 2:
                    if (this.vendingMachine == null){
                        System.out.println("Vending machine does not exist; create one.");
                        break;
                    }
                    RegularVendingMachineController testRegular = new RegularVendingMachineController(textInterface, vendingMachine, scanner);
                    testRegular.testingMenu();
                    break;
                case 3:
                    this.isRunning = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
            }
        }
        scanner.close();
    }

    // Getters, setters
}
