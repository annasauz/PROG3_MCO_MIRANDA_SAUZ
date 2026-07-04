import java.util.Scanner;

public class RegularVendingMachineController {
    private final TextInterface textInterface;
    private final RegularVendingMachine vendingMachine;
    private final Scanner choice;


    // Constructor
    public RegularVendingMachineController(TextInterface textInterface, RegularVendingMachine vendingMachine, Scanner scanner) {
        this.textInterface = textInterface;
        this.vendingMachine = vendingMachine;
        this.choice = scanner;
    }


    // Methods
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

    private void vendingFeatures() {

    boolean isRunningVending = true;

    while (isRunningVending) {

        textInterface.printVendingFeatures();
        int choice = getInput(1, 5);

        switch (choice) {

            case 1:
                displayItemsHandler();
                break;

            case 2:
                addMoneyHandler();
                break;

            case 3:
                purchaseHandler();
                break;

            case 4:
                vendingMachine.produceChangeWithoutPurchase();
                break;

            case 5:
                isRunningVending = false;
                break;

            default:
                System.out.println("Invalid user choice");
        }
    }
}

    private void maintenanceFeatures() {

    boolean isRunningMaintenance = true;

    while (isRunningMaintenance) {

        textInterface.printMaintenanceFeatures();
        int choice = getInput(1, 6);

        switch (choice) {

            case 1:
                maintenancePriceHandler();
                break;

            case 2:
                maintenanceStockHandler();
                break;

            case 3:
                maintenanceAddChangeHandler();
                break;

            case 4:
                vendingMachine.collectMoney();
                break;

            case 5:
                vendingMachine.printTransactionSummary();
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

        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        vendingMachine.receivePayment(denomination, quantity);
        System.out.println();
    }

    /**
     * Handles the purchase of an item from the vending machine.
     */

    private void purchaseHandler() {
        System.out.println("\n--- Purchase Item ---");
        displayItemsHandler();

        System.out.print("Select slot (1-8): ");
        int slotIndex = getInput(1, 8)-1;

        boolean success = vendingMachine.purchaseItem(slotIndex);
        System.out.println();
    }

    private void maintenanceStockHandler(){
        System.out.println("Re-stock slot 1-8:");
        int choice = getInput(1,8)-1;
        this.choice.nextLine();


        System.out.println("Item name: ");
        String itemName = this.choice.nextLine();

        System.out.println("Item price: ");
        double itemPrice = this.choice.nextDouble();

        System.out.println("Item calories: ");
        double itemCalories = this.choice.nextDouble();

        System.out.println("Item quantity to stock: ");
        int itemStock = this.choice.nextInt();

        Item item = new Item(itemName, itemPrice, itemCalories);
        if (itemStock <= 0) {
        System.out.println("Invalid quantity.");
        return;
        }

        vendingMachine.restockSlot(choice, item, itemStock);
        System.out.println("Item stocked.");
    }

private void maintenancePriceHandler() {

    System.out.println("Re-price slot 1-8: ");
    int choice = getInput(1, 8) - 1;

    Item item = vendingMachine.getItemTemplates()[choice];

    if (item == null) {
        System.out.println("Item does not exist.");
    }
    else {

        boolean validPrice = false;
        double itemPrice = 0;

        while (!validPrice) {

            System.out.println("Input new price: ");

            if (this.choice.hasNextDouble()) {

                itemPrice = this.choice.nextDouble();

                if (itemPrice >= 0) {
                    validPrice = true;
                }
                else {
                    System.out.println("Price cannot be negative.");
                }

            }
            else {
                System.out.println("Invalid input. Please enter a numeric price.");
                this.choice.next();
            }
        }

        if (item.setPrice(itemPrice)) {
            System.out.println("Price updated.");
        }
        else {
            System.out.println("Invalid price.");
        }
    }
}

    private void maintenanceAddChangeHandler(){
        System.out.println("Add change denomination to replenish (1,5,10,20,50,100,200,500,1000): ");
        int denomination = getMoneyDenomination();
        System.out.println("Enter quantity:");
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
private int getInput(int min, int max) {

    while (true) {

        if (!choice.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            choice.next();
            continue;
        }

        int input = choice.nextInt();

        if (input >= min && input <= max) {
            return input;
        }

        System.out.println("Please enter a number between "
                + min + " and " + max + ".");

    }
}

private int getPositiveInteger() {

    while (true) {

        if (!choice.hasNextInt()) {
            System.out.println("Invalid input. Enter a whole number.");
            choice.next();
            continue;
        }

        int value = choice.nextInt();

        if (value > 0) {
            return value;
        }

        System.out.println("Value must be greater than zero.");
    }
}


private int getMoneyDenomination() {

    while (true) {

        if (!choice.hasNextInt()) {
            System.out.println("Invalid input.");
            choice.next();
            continue;
        }

        int denomination = choice.nextInt();

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
                return denomination;
        }

        System.out.println("Invalid denomination.");
    }
}

}
