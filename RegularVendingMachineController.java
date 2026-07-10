import java.util.Scanner;

public class RegularVendingMachineController {
    private final TextInterface textInterface;
    private final RegularVendingMachine vendingMachine;
    private final Scanner choice;

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
        this.choice = scanner;
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
            int option = getInput(1, 3); // Reduced to 3 options due to consolidation

            switch (option) {
                case 1:
                    displayItemsHandler();
                    pauseScreen();
                    break;
                case 2:
                    purchaseHandler(); // Add money & Return Change are now in here
                    pauseScreen();
                    break;
                case 3:
                    isRunningVending = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
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
                    pauseScreen();
                    break;
                case 2:
                    maintenanceStockHandler();
                    pauseScreen();
                    break;
                case 3:
                    maintenanceAddChangeHandler();
                    pauseScreen();
                    break;
                case 4:
                    vendingMachine.collectMoney();
                    pauseScreen();
                    break;
                case 5:
                    vendingMachine.printTransactionSummary();
                    pauseScreen();
                    break;
                case 6:
                    isRunningMaintenance = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
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
            System.out.println("\n--- Purchase Menu ---");
            System.out.println("1. Insert Money");
            System.out.println("2. Select Item to Buy");
            System.out.println("3. Cancel and Return Change");
            System.out.print("Choice: ");
            int choice = getInput(1, 3);

            switch (choice) {
                case 1:
                    addMoneyHandler();
                    break;
                case 2:
                   displayItemsHandler();
                    System.out.print("Select slot (1-8): ");
                    int slotIndex = getInput(1, 8) - 1;
                    boolean success = vendingMachine.purchaseItem(slotIndex);
                    if (success) {
                        isPurchasing = false; // Exit purchase loop after successful buy
                    }
                    break;
                case 3:
                    vendingMachine.produceChangeWithoutPurchase();
                    isPurchasing = false;
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

        System.out.print("Select slot to restock (1-8): ");
        int slotChoice = getInput(1, 8) - 1;

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

    /**
     * Allows user to update price/s of items
     */
    private void maintenancePriceHandler() {
        displayItemsHandler();
        System.out.println("Re-price slot 1-8: ");
        int slotChoice = getInput(1, 8) - 1;

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

    /**
     * Allows user to replenish change inside the vending machine
     */
    private void maintenanceAddChangeHandler(){
        System.out.println("Add change denomination to replenish (1,5,10,20,50,100,200,500,1000): ");
        int denomination = getMoneyDenomination();
        System.out.println("Enter quantity:");
        int quantity = getPositiveInteger();
        vendingMachine.replenishChangeReserves(denomination,quantity);
    }


    /**
     * Pauses the program's execution to allow the user to read the console output.
     * to prevent the main menu loop from instantly clearing the screen 
     * before user has time to view their transaction or the item list.
     */
    private void pauseScreen() {
        System.out.println("\nType '0' and press Enter to return to menu...");
        boolean paused = true;
        while (paused) {
            String input = this.choice.nextLine().trim();
           if (!input.isEmpty()) { // not empty line
                if (input.equals("0")) {
                    paused = false; 
                } else {
                    System.out.println("Invalid input. Please type '0' and press Enter to return...");
                }
            }
        }
    }



    /**
     * Gets user input and validates it.
     *
     * @param min smallest user choice
     * @param max largest user choice
     * @return the user choice if within range, otherwise returns 0
     */
    private int getInput(int min, int max) {
        boolean valid = false;
        int input = 0;
        while (!valid) {
            if (!choice.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                choice.next(); // consume bad input
            } else {
                input = choice.nextInt();
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            }
        }
        return input;
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
            if (!choice.hasNextInt()) {
                System.out.println("Invalid input. Enter a whole number.");
                choice.next();
            } else {
                value = choice.nextInt();
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
            if (!choice.hasNextDouble()) {
                System.out.println("Invalid input. Enter a valid number/decimal.");
                choice.next();
                                         } 
            else {
                value = choice.nextDouble();
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
            if (!choice.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                choice.next();
            } else {
                denomination = choice.nextInt();
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
