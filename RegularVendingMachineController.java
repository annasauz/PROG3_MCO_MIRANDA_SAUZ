import java.util.Scanner;

public class RegularVendingMachineController {
    private final TextInterface textInterface;
    private final RegularVendingMachine vendingMachine;
    private final Scanner scanner;
    private boolean isRunning;

    public RegularVendingMachineController(TextInterface textInterface, RegularVendingMachine vendingMachine) {
        this.textInterface = textInterface;
        this.vendingMachine = vendingMachine;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
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

        scanner.close();
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

        System.out.print("Enter money: ");
        int denomination = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

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

        System.out.print("Select slot (0-7): ");
        int slotIndex = getInput(0, 7);

        boolean success = vendingMachine.purchaseItem(slotIndex);
        System.out.println();
    }

    /**
     * Prints transaction summary and asks user whether or not to return remaining credit.
     */

    private void exitHandler() {
        System.out.println("\n--- Transaction Complete ---");
        vendingMachine.printTransactionSummary();

        System.out.print("Return remaining credit? (y/n): ");
        String response = scanner.next();

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

        int choice = scanner.nextInt();
        boolean inMinMax = choice >= min && choice <= max;

        if (inMinMax){
            System.out.println(choice);
            return choice;
        }
        return 0;
    }
}
