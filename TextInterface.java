import java.util.Scanner;

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
    public void printCreateAndTest() {
        clearScreen();

        System.out.println("==================================");
        System.out.println("      VENDING MACHINE FACTORY");
        System.out.println("==================================");
        System.out.println("1. Create Vending Machine");
        System.out.println("2. Test Vending Machine");
        System.out.println("3. Exit");
        System.out.println("==================================");
        System.out.print("Choice: ");
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
     * Displays the type of vending machine to be created.
     */
    public void printVendingMachineType(){
        System.out.println("Select Vending Machine Type:");
        System.out.println("1. Regular Vending Machine");
        System.out.println("2. Special Vending Machine");
    }

    public void printTypeOfVendingMachineToTest() {
        System.out.println("Select Vending Machine Type to Test:");
        System.out.println("1. Regular Vending Machine");
        System.out.println("2. Special Vending Machine");
    }
 
    /**
     * Displays purchase menu
     */
    public void purchaseMenu() {
        System.out.println("\n--- Purchase Menu ---");
        System.out.println("1. Insert Money");
        System.out.println("2. Select Regular Item to Buy");
        System.out.println("3. Build Custom Milk Tea"); 
        System.out.println("4. Cancel and Return Change");
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

    printDivider();
    System.out.println("                REGULAR VENDING MACHINE");
    printDivider();

    System.out.printf("%-6s %-15s %-10s %-10s %-8s%n",
            "Slot", "Item", "Price", "Calories", "Stock");

    printDivider();

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

    printDivider();
}

    /**
     * Displays the menu of items available in the special vending machine.
     *
     * @param items Array of Item objects representing the items in the vending machine
     * @param slots Array of SlotCompartment objects representing the slots in the vending machine
     */
    public void printSpecialVendingMachineMenu(Item[] items, SlotCompartment[] slots) {

        printDivider();
        System.out.println("                SPECIAL VENDING MACHINE");
        printDivider();

        System.out.printf("%-6s %-15s %-10s %-10s %-8s%n",
                "Slot", "Item", "Price", "Calories", "Stock");

        printDivider();

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

            String itemName = items[i].getName();

            // Label restricted ingredients
            if (items[i] instanceof SpecialItem) {

                SpecialItem specialItem = (SpecialItem) items[i];

                if (!specialItem.isSellableIndividually()) {
                    itemName += " (Restricted)";
                }
            }

            System.out.printf("%-6d %-28s PHP%-9.2f %-10.0f %-8d%n",
                i + 1,
                itemName,
                items[i].getPrice(),
                items[i].getCalories(),
                slots[i].getCurrentInSlotItems());

            } else {

                System.out.printf("%-6d %-28s %-10s %-10s %-8d%n", i + 1, "Empty", "-", "-", 0);
                }
            }
        printDivider();
    }

    public void printCustomMilkTeaSummary(SpecialVendingMachine specialMachine, int tea, int milk, int sweetener, int sugarLevel, int iceLevel, int size, java.util.List<Integer> addons) {
        System.out.println("\n========== ORDER SUMMARY ==========");

        System.out.println("Tea Base : " + specialMachine.getItemTemplates()[tea].getName());
        System.out.println("Milk Base: " + specialMachine.getItemTemplates()[milk].getName());

        // Sweetener
        System.out.print("Sweetener: ");

        if (sweetener == -1) {
            System.out.println("None");
        }
        else {
            System.out.println(specialMachine.getItemTemplates()[sweetener].getName());
        }

        // Sugar Level
        System.out.print("Sugar Level: ");

        if (sweetener == -1) {

            System.out.println("N/A");

        } else {

            switch (sugarLevel) {

                case SpecialVendingMachine.NO_SUGAR:
                System.out.println("0%");
                break;

                case SpecialVendingMachine.HALF_SUGAR:
                System.out.println("50%");
                break;
                
                case SpecialVendingMachine.FULL_SUGAR:
                System.out.println("100%");
                break;
            }
        }

        System.out.print("Ice Level: ");

        switch (iceLevel) {

            case SpecialVendingMachine.NO_ICE:
                System.out.println("No Ice");
                break;

            case SpecialVendingMachine.LESS_ICE:
                System.out.println("Less Ice");
                break;

            case SpecialVendingMachine.REGULAR_ICE:
                System.out.println("Regular Ice");
                break;

            case SpecialVendingMachine.EXTRA_ICE:
                System.out.println("Extra Ice");
                break;
        }
        System.out.print("Size     : ");

        switch (size) {

            case SpecialVendingMachine.SMALL:
                System.out.println("Small");
                break;

            case SpecialVendingMachine.MEDIUM:
                System.out.println("Medium");
                break;

            case SpecialVendingMachine.LARGE:
                System.out.println("Large");
                break;
        }

        System.out.println("Add-ons  :");

        if (addons.isEmpty()) {

            System.out.println("None");

        } else {

            for (Integer slot : addons) {

                System.out.println("- "
                        + specialMachine.getItemTemplates()[slot].getName());

            }
        }

        System.out.println();
    }

    public void printMilkTeaToBuy() {
        System.out.println("\n--- Custom Milk Tea ---");
        System.out.println("1. Create custom milk tea");
        System.out.println("2. Purchase signature milk tea");
        System.out.println("3. Randomized milk tea");
        System.out.println("4. Cancel");
        System.out.print("Choice: ");
    }

    /**
     * Prints a horizontal bar to the console for visual separation.
     */
    private void printDivider(){
        System.out.println("=".repeat(62));

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

    public void printMaintenanceStockDisplay(Item[] items, SlotCompartment[] slots) {

        printDivider();
        System.out.println("           CURRENT INVENTORY");
        printDivider();

        System.out.printf("%-6s %-22s %-10s%n", "Slot", "Item", "Stock");

        printDivider();

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

                int current = slots[i].getCurrentInSlotItems();
                int maximum = slots[i].getMaximumInSlotItems();

                System.out.printf("%-6d %-22s %d/%d", i + 1, items[i].getName(), current, maximum);

                if (current <= 3) {
                    System.out.print("   !! LOW STOCK !!");
            }

        System.out.println();

            } else {

                int current = 0;
                int maximum = slots[i].getMaximumInSlotItems();

                System.out.printf("%-6d %-22s %d/%d", i + 1, "Empty", current, maximum);

                System.out.print("   !! LOW STOCK !!");

                System.out.println();
                }
            }

            printDivider();
        }

}
