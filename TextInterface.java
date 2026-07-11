import java.util.Scanner;

/**
 * Provides methods to display various menus and prompts to the user.
 */
public class TextInterface {
    /**
     * Clears screen
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays main menu
     */
    public void printCreateAndTest(){
        clearScreen();
        System.out.println("Main menu");
        System.out.println("1. Create vending machine");
        System.out.println("2. Test vending machine");
        System.out.println("3. Exit");

    }

    /**
     * Displays test menu
     */
    public void printTestMenu(){
        clearScreen();
        System.out.println("Test menu");
        System.out.println("1. Test vending features");
        System.out.println("2. Test maintenance features");
        System.out.println("3. Exit");
        System.out.print("Menu Choice: ");

    }

    /**
     * Displays vending features menu
     */
    public void printVendingFeatures(){
        clearScreen();
        System.out.println("Vending Features");
        System.out.println("------------------------------");
        System.out.println("1. Display items");
        System.out.println("2. Purchase Item / Insert Money");
        System.out.println("3. Exit");
        System.out.print("Vending Choice: ");

    }

    /**
     * Displays purchase menu
     */
    public void purchaseMenu() {
        System.out.println("\n--- Purchase Menu ---");
        System.out.println("1. Insert Money");
        System.out.println("2. Select Item to Buy");
        System.out.println("3. Cancel and Return Change");
        System.out.print("Purchase Choice: ");
    }

    /**
     * Displays maintenance features menu
     */
    public void printMaintenanceFeatures() {

    clearScreen();

    System.out.println("Maintenance Features");
    System.out.println("------------------------------");
    System.out.println("1. Set Price");
    System.out.println("2. Restock Item");
    System.out.println("3. Replenish Change");
    System.out.println("4. Collect Money");
    System.out.println("5. Print Transaction Summary");
    System.out.println("6. Exit");

    System.out.print("Maintenance Choice: ");

}
    /**
     * Displays the main menu of the vending machine application.
     */
    public void printMainMenu() {
        System.out.println("=========================");
        System.out.println("    Vending Machine    ");
        System.out.println("=========================");
        System.out.println("1. Display Items");
        System.out.println("2. Purchase Item");
        System.out.println("3. Exit");
    }

    /**
     * Displays the menu of items available in the regular vending machine.
     *
     * @param items Array of Item objects representing the items in the vending machine
     * @param slots Array of SlotCompartment objects representing the slots in the vending machine
     */
  public void printRegularVendingMachineMenu(Item[] items, SlotCompartment[] slots) {

    System.out.println("==============================================================");
    System.out.println("                REGULAR VENDING MACHINE");
    System.out.println("==============================================================");

    System.out.printf("%-6s %-15s %-10s %-10s %-8s%n",
            "Slot", "Item", "Price", "Calories", "Stock");

    System.out.println("--------------------------------------------------------------");

    for (int i = 0; i < items.length; i++) {

        if (items[i] != null) {

            System.out.printf("%-6d %-15s PHP%-9.2f %-10.0f %-8d%n",
                    i + 1,
                    items[i].getName(),
                    items[i].getPrice(),
                    items[i].getCalories(),
                    slots[i].getCurrentInSlotItems());

        }
        else {

            System.out.printf("%-6d %-15s %-10s %-10s %-8d%n",
                    i + 1,
                    "Empty",
                    "-",
                    "-",
                    0);

        }
    }

    System.out.println("==============================================================");
}

    /**
    * Pauses the program until the user presses Enter.
    *
    * @param scanner shared Scanner object
    */
    public void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}
