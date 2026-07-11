import java.util.Scanner;

public class RegularVendingMachineController {
    private TextInterface textInterface;
    private RegularVendingMachine vendingMachine;
    private Scanner scanner;

    // Constructor
    /**
     * Creates a controller for a vending mahine
     *
     * @param textInterface shared instance of TextInterface
     * @param vendingMachine instance of RegularVendingMachine
     * @param scanner shared scanner
     */
    public RegularVendingMachineController(TextInterface textInterface, RegularVendingMachine vendingMachine, Scanner scanner) {
        this.textInterface = textInterface;
        this.vendingMachine = vendingMachine;
        this.scanner = scanner;
    }

    // Methods
    /**
     * Prints test menu (vending and maintenance tests)
     */
    public void testingMenu() {

    boolean isRunningTest = true;

    while (isRunningTest) {
        textInterface.printTestMenu();

        int choice = getInput(1, 3);

        switch (choice) {

            case 1:
                vendingFeatures();
                break;

            case 2:
                maintenanceFeatures();
                break;

            case 3:
                isRunningTest = false;
                break;

            default:
                System.out.println("Invalid user choice");
                textInterface.pressEnterToContinue(scanner);
        }
    }
}

    /**
     * Prints and allows users to choose regular vending machine features
     */
    private void vendingFeatures() {
        boolean isRunningVending = true;


        while (isRunningVending) {
            textInterface.printVendingFeatures();

            int option = getInput(1, 3);

            switch (option) {
                case 1:
                    displayItemsHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 2:
                    purchaseHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 3:
                    isRunningVending = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Prints and allows users to maintain the vending machine
     */
    private void maintenanceFeatures() {

        boolean isRunningMaintenance = true;

        while (isRunningMaintenance) {

            textInterface.printMaintenanceFeatures();

            int option = getInput(1, 6);

            switch (option) {
                case 1:
                    maintenancePriceHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 2:
                    maintenanceStockHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 3:
                    maintenanceAddChangeHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 4:
                    vendingMachine.collectMoney();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 5:
                    vendingMachine.printTransactionSummary();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 6:
                    isRunningMaintenance = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
            }
        }
    }


    /**
     * Displays the items available in the vending machine.
     */
    private void displayItemsHandler() {

    System.out.println();

    textInterface.printRegularVendingMachineMenu(
            vendingMachine.getItemTemplates(),
            vendingMachine.getSlots());

    System.out.println();
    }

    /**
     * Handles the addition of money to the vending machine.
     */
    private void addMoneyHandler() {
        System.out.println("\n--- Add Money ---");
        displayItemsHandler();
        System.out.print("Enter denomination of money (1,5,10,20,50,100,200,500,1000): ");
        int denomination = getMoneyDenomination();

        System.out.print("Enter quantity: ");
        int quantity = getPositiveInteger();

        vendingMachine.receivePayment(denomination, quantity);
        System.out.println();
    }

    /**
     * Handles the purchase of an item from the vending machine.
     */
    private void purchaseHandler() {
        boolean isPurchasing = true;
        while (isPurchasing) {

            textInterface.purchaseMenu();

            int choice = getInput(1, 3);

            switch (choice) {
                case 1:
                    addMoneyHandler();
                    break;
                case 2:
                    displayItemsHandler();

                    System.out.print("\nSelect an item to purchase (1-8): ");
                    int slotChoice = getInput(1, 8) - 1;
                    boolean success = vendingMachine.purchaseItem(slotChoice);

                    if (success) {
                        isPurchasing = false;
                        }
                    break;
                case 3:
                    vendingMachine.produceChangeWithoutPurchase();
                    isPurchasing = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
                    break;    
            }
        }
    }

    /**
     * Handles maintenance of stock of an item.
     */
    private void maintenanceStockHandler() {
        System.out.println("\n--- Restock Items ---");
        displayItemsHandler();

        System.out.print("Select a slot to restock (1-8): ");
        int userInput = getInput(1, 8);
       if (userInput <= 0) {
            System.out.println("Invalid slot choice. Returning to menu.");
        } else {
            int slotChoice = userInput - 1; 

            Item existingItem = vendingMachine.getItemTemplates()[slotChoice];

            if (existingItem != null) {

                System.out.println("\nCurrent item: " + existingItem.getName());

                System.out.print("Enter quantity to add: ");
                int quantity = getPositiveInteger();

                vendingMachine.restockSlot(slotChoice, existingItem, quantity);

            } else {
                System.out.println("Unable to restock. The selected slot has not been initialized.");
            }
        }
    }

    /**
     * Allows user to update price/s of items
     */
    private void maintenancePriceHandler() {
        displayItemsHandler();
        System.out.print("Select an item to update (1-8): ");
        int userInput = getInput(1, 8); 
        if (userInput <= 0) {
            System.out.println("Invalid slot choice. Returning to menu.");
        } else {           
            int slotChoice = userInput - 1; 
            Item item = vendingMachine.getItemTemplates()[slotChoice];

            if (item == null) {
                System.out.println("Item does not exist in this slot.");
            } else {
                System.out.print("Input new price: ");
                double itemPrice = getPositiveDouble();                
                if (item.setPrice(itemPrice)) {
                    System.out.println("Price updated successfully.");
                } else {
                    System.out.println("Invalid price.");
                }
            }
        }
    }

    /**
     * Allows user to replenish change inside the vending machine
     */
    private void maintenanceAddChangeHandler(){
        System.out.print("Add change denomination to replenish (1,5,10,20,50,100,200,500,1000): ");
        int denomination = getMoneyDenomination();
        System.out.print("Enter quantity:");
        int quantity = getPositiveInteger();
        vendingMachine.replenishChangeReserves(denomination,quantity);
    }

    /**
     * Gets user input and validates it.
     *
     * @param min smallest user choice
     * @param max largest user choice
     * @return the user choice if within range, otherwise returns 0
     */
    private int getInput(int min, int max){
        if (max < min){
            System.out.println("Invalid input call");
        }
        if (!scanner.hasNextInt()) {
        scanner.next(); 
        return 0;       
                                    }
        int choice = scanner.nextInt();
        boolean inMinMax = choice >= min && choice <= max;

        if (inMinMax){
            return choice;
        }
        return 0;
    }
    /**
     * Checks if valid positive integer.
     *
     * @return checked integer
     */
    private int getPositiveInteger() {
        boolean valid = false;
        int value = 0;
        while (!valid) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a whole number.\n");
                scanner.next();
            } else {
                value = scanner.nextInt();
                if (value > 0) {
                    valid = true;
                } else {
                    System.out.println("Value must be greater than zero. Try again:");
                }
            }
        }
        return value;
    }

    /**
     * Checks if valid positive double.
     *
     * @return checked double
     */
    private double getPositiveDouble() {
        boolean valid = false;
        double value = 0;
        while (!valid) {
            if (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Enter a valid number/decimal.");
                scanner.next();
                                         } 
            else {
                value = scanner.nextDouble();
                if (value > 0)  {
                    valid = true;
                                } 
                else {
                    System.out.println("Value must be greater than zero. Try again:");
                     }
                }
                      }
        return value;
    }

    /**
     * Checks if valid denomination.
     *
     * @return checked denomination
     */
    private int getMoneyDenomination() {
        boolean valid = false;
        int denomination = 0;
        while (!valid) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
            } else {
                denomination = scanner.nextInt();
                switch (denomination) {
                    case 1:
                    case 5: 
                    case 10: 
                    case 20: 
                    case 50: 
                    case 100: 
                    case 200: 
                    case 500: 
                    case 1000:
                        valid = true;
                        break;
                    default:
                        System.out.println("Invalid denomination. Please try again: ");
                        break;
                }
            }
        }
        return denomination;
    }

}



