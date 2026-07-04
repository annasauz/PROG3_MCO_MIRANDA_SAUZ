import java.util.Scanner;

public class RegularVendingMachineController {
    private final TextInterface textInterface;
    private final RegularVendingMachine vendingMachine;
    private final Scanner choice;
    private boolean isRunning;

    // Constructor
    public RegularVendingMachineController(TextInterface textInterface, RegularVendingMachine vendingMachine, Scanner scanner) {
        this.textInterface = textInterface;
        this.vendingMachine = vendingMachine;
        this.choice = scanner;
        this.isRunning = true;
    }


    // Methods
    public void testingMenu(){
        boolean isRunningTest = true;

        while (isRunningTest){
            textInterface.printTestMenu();
            int choice = this.choice.nextInt();

            switch (choice){
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

    private void vendingFeatures(){
        boolean isRunningVending = true;
        textInterface.printVendingFeatures();
        int choice = this.choice.nextInt();

        switch (choice){
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

    private void maintenanceFeatures(){
        boolean isRunningMaintenance = true;
        textInterface.printMaintenanceFeatures();
        int choice = this.choice.nextInt();

        switch (choice){
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
                vendingMachine.printTransactionSummary();
                break;
            case 5:
                isRunningMaintenance = false;
                break;
            default:
                System.out.println("Invalid user choice");
        }
    }

    /**
     * Starts the regular vending machine application, displaying the main menu and handling user input.
     */
    public void startRegularVendingMachine() {
        System.out.println("\n=== Regular Vending Machine Started ===\n");
        
        while (isRunning) {
            textInterface.printMainMenu();
            System.out.print("Select option: ");
            int choice = getInput(1,3);
            mainMenuHandler(choice);
        }

        choice.close();
    }


    /**
     * Handles the main menu options based on user input.
     *
     * @param choice the user's menu choice
     */
    private void mainMenuHandler(int choice){
        switch (choice){
            case (1):
                displayItemsHandler();
                break;
            case (2):
                purchaseHandler();
                break;
            case (3):
                exitHandler();
                break;
            default:
                System.out.println("Invalid choice.");
        }

    }

    /**
     * Displays the items available in the vending machine.
     */
    private void displayItemsHandler(){
        System.out.println();
        textInterface.printRegularVendingMachineMenu(vendingMachine.getItemTemplates());
        System.out.println();
    }


    /**
     * Handles the addition of money to the vending machine.
     */

    private void addMoneyHandler() {
        System.out.println("\n--- Add Money ---");

        System.out.print("Enter denomination of money (1,5,10,20,50,100,200,500,1000): ");
        int denomination = choice.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = choice.nextInt();

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
        vendingMachine.restockSlot(choice, item,itemStock);
        System.out.println("Item stocked.");
    }

    private void maintenancePriceHandler(){
        System.out.println("Re-price slot 1-8: ");
        int choice = getInput(1, 8)-1;
        Item item = vendingMachine.getItemTemplates()[choice];

        if (item == null){
            System.out.println("Item does not exist");
            return;
        }

        System.out.println("Input new price: ");
        double itemPrice = this.choice.nextDouble();

        item.setPrice(itemPrice);
        System.out.println("Price set.");
    }

    private void maintenanceAddChangeHandler(){
        System.out.println("Add change denomination to replenish (1,5,10,20,50,100,200,500,1000): ");
        int denomination = choice.nextInt();
        System.out.println("Enter quantity:");
        int quantity = choice.nextInt();
        vendingMachine.replenishChangeReserves(denomination,quantity);
    }

    /**
     * Prints transaction summary and asks user whether or not to return remaining credit.
     */

    private void exitHandler() {
        System.out.println("\n--- Transaction Complete ---");
        vendingMachine.printTransactionSummary();

        System.out.print("Return remaining credit? (y/n): ");
        String response = choice.next();

        if (response.equalsIgnoreCase("y")) {
            vendingMachine.produceChangeWithoutPurchase();
        }

        isRunning = false;
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

        int choice = this.choice.nextInt();
        boolean inMinMax = choice >= min && choice <= max;

        if (inMinMax){
            return choice;
        }
        return 0;
    }
}
