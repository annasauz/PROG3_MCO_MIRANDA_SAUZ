import java.util.Scanner;

public class VendingMachineSoftwareLoop {
    private TextInterface textInterface;
    private RegularVendingMachine vendingMachine;
    private SpecialVendingMachine specialVendingMachine;
    private boolean isRunning;
    private Scanner scanner = new Scanner(System.in);

    // Constructor
    /**
     * Creates a software loop for the program
     * Precondition: textInterface is a valid instance of TextInterface
     * Postcondition: A new instance of VendingMachineSoftwareLoop is created with the provided TextInterface and no vending machine initialized.
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
     * Precondition: The software loop is initialized and ready to run.
     * Postcondition: The software loop is executed until the user chooses to exit.
     */
    public void run() {
        while (isRunning) {
            textInterface.printCreateAndTest();
            int choice = getInput(1, 3);

            switch (choice){
                case 1:
                    textInterface.printVendingMachineType();
                    switch (getInput(1, 2)) {
                        case 1:
                            this.vendingMachine = new RegularVendingMachine();
                            System.out.println("\nRegular Vending Machine successfully created.");
                            break;
                        case 2:
                            System.out.println("\n==================================");
                            System.out.println("      TEST VENDING MACHINE");
                            System.out.println("==================================");
                            textInterface.printTypeOfVendingMachineToTest();
                            this.specialVendingMachine = new SpecialVendingMachine();
                            System.out.println("\nSpecial Vending Machine successfully created.");
                            break;
                    }
                    break;
                case 2:
                    textInterface.printTypeOfVendingMachineToTest();
                    switch(getInput(1, 2)) {
                        case 1:
                            if (this.vendingMachine == null){
                                System.out.println("\nNo Regular Vending Machine has been created yet.");
                                System.out.println("Please create one first.");
                                textInterface.pressEnterToContinue(scanner);
                                break;
                            }
                            RegularVendingMachineController regularController = new RegularVendingMachineController(textInterface, vendingMachine, scanner);
                            regularController.testingMenu();
                            textInterface.pressEnterToContinue(scanner);
                            break;
                       case 2:
                            if (this.specialVendingMachine == null) {
                                System.out.println("\nNo Special Vending Machine has been created yet.");
                                System.out.println("Please create one first.");
                                textInterface.pressEnterToContinue(scanner);
                                break;
                            }
                            RegularVendingMachineController controller = new RegularVendingMachineController(textInterface, specialVendingMachine, scanner);
                            controller.testingMenu();
                            textInterface.pressEnterToContinue(scanner);
                            break;
                        }
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
    * Precondition: The user is prompted to enter a choice within the specified range.
    * Postcondition: The method returns a valid integer input from the user within the specified range.
    *
    * @param min minimum valid choice
    * @param max maximum valid choice
    * @return validated user input
    */
    private int getInput(int min, int max) {
        boolean valid = false;
        int input = 0;
        String leftover;

        while (!valid) {
            System.out.print("Menu Choice: ");

            // check if num
            if (scanner.hasNextInt()) {
                input = scanner.nextInt(); 
                leftover = scanner.nextLine().trim();

                if (!leftover.isEmpty()) {
                    System.out.println("Invalid input. Please enter only one whole number.");
                }
                
                else if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between "
                            + min + " and " + max + ".");
                }
            } else {
                // for letter and symbols
                System.out.println("Invalid input. Please enter a whole number.");
                
                // clear to avoid looping
                scanner.nextLine(); 
            }
        }

        return input;
    }

}
