import java.util.Scanner;

public class VendingMachineSoftwareLoop {
    private TextInterface textInterface;
    private RegularVendingMachine vendingMachine;
    private boolean isRunning;
    private Scanner scanner = new Scanner(System.in);

    // Constructor
    /**
     * Creates a software loop for the program
     *
     * @param textInterface shared instance of TextInterface
     */
    public VendingMachineSoftwareLoop(TextInterface textInterface) {
        this.textInterface = textInterface;
        this.vendingMachine = null;
        this.isRunning = true;
    }

    // Methods
    /**
     * Runs the software loop
     */
    public void run() {
        while (isRunning) {
            textInterface.printCreateAndTest();
            int choice = getInput(1, 3);

            switch (choice){
                case 1:
                    this.vendingMachine = new RegularVendingMachine();
                    System.out.println("Vending machine Created.");
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 2:
                    if (this.vendingMachine == null){
                        System.out.println("Vending machine does not exist; create one.");
                        textInterface.pressEnterToContinue(scanner);
                        break;
                    }
                    RegularVendingMachineController testRegular = new RegularVendingMachineController(textInterface, vendingMachine, scanner);
                    testRegular.testingMenu();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 3:
                    this.isRunning = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
            }
        }
        scanner.close();
    }

    /**
    * Gets user input and validates it.
    *
    * @param min minimum valid choice
    * @param max maximum valid choice
    * @return validated user input
    */
    private int getInput(int min, int max) {
        boolean valid = false;
        int input = 0;

        while (!valid) {
            System.out.print("Menu Choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next(); // Consume invalid input
            } else {
                input = scanner.nextInt();

                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between "
                            + min + " and " + max + ".");
                }
            }
        }

        return input;
    }

}
