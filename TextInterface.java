import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the text-based interface for the vending machine application.
 * Provides methods to display menus, prompts, and information to the user.
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
    public void printCreateAndTest() {
        clearScreen();
        printShortDivider();
        System.out.println("          VENDING MACHINE FACTORY");
        printShortDivider();
        System.out.println("1. Create Vending Machine");
        System.out.println("2. Test Vending Machine");
        System.out.println("3. Exit");
        printShortDivider();
    }

    /**
     * Displays test menu
     */
    public void printTestMenu(){
        clearScreen();
        System.out.println("Test menu");
        printShortDivider();
        System.out.println("1. Test vending features");
        System.out.println("2. Test maintenance features");
        System.out.println("3. Exit");


    }

    /**
     * Displays vending features menu
     */
    public void printVendingFeatures(){
        clearScreen();
        System.out.println("Vending Features");
        printShortDivider();
        System.out.println("1. Display items");
        System.out.println("2. Purchase Item / Insert Money");
        System.out.println("3. Exit");

    }
    /**
     * Displays the type of vending machine to be created.
     */
    public void printVendingMachineType(){
        clearScreen();
        System.out.println("Select Vending Machine Type");
        printShortDivider();
        System.out.println("1. Regular Vending Machine");
        System.out.println("2. Special Vending Machine");
    }

    /**
     * Displays the menu for selecting the type of vending machine to test.
     */
    public void printTypeOfVendingMachineToTest() {
        clearScreen();
        System.out.println("Select Vending Machine Type to Test");
        printShortDivider();
        System.out.println("1. Regular Vending Machine");
        System.out.println("2. Special Vending Machine");
    }
 
    /**
     * Displays REGULAR purchase menu
     */
    public void purchaseMenuRegular() {
        clearScreen();
        System.out.println("Purchase Menu");
        printShortDivider();
        System.out.println("1. Insert Money");
        System.out.println("2. Select Item to Buy");
        System.out.println("3. Cancel and Return Change");

    }

    /**
     * Displays SPECIAL purchase menu0
     */

    public void purchaseMenuSpecial() {
        clearScreen();
        System.out.println("Purchase Menu");
        printShortDivider();
        System.out.println("1. Insert Money");
        System.out.println("2. Select Individual Item to Buy");
        System.out.println("3. Special Milk Tea");
        System.out.println("4. Cancel and Return Change");

    }

    /**
     * Displays maintenance features menu
     */
    public void printMaintenanceFeatures(boolean specialMachine) {
        clearScreen();
        System.out.println("Maintenance Features");
        printShortDivider();
        System.out.println("1. Set Price");
        System.out.println("2. Restock Item");
        System.out.println("3. Replenish Change");
        System.out.println("4. Collect Money");
        System.out.println("5. Print Transaction Summary");
        System.out.println("6. View Change Inventory");
        if (specialMachine) {
            System.out.println("7. Machine Insights");
        System.out.println("8. Exit");
        } else {
            System.out.println("7. Exit");
        }
    }

    /**
     * Displays the available signature milk tea drinks together with
    * their fixed cup sizes and prices.
    */
    public void printSignatureDrinks() {
        clearScreen();
        System.out.println("\nSignature Drinks");
        System.out.println("==========================================");
        System.out.println("Signature drinks have fixed recipes,");
        System.out.println("sizes, and prices.");
        System.out.println();

        System.out.println("1. Roasted Oolong Matcha Latte");
        System.out.println("   Medium: PHP 315.00");
        System.out.println();

        System.out.println("2. Classic Brown Sugar Earl Grey Cheese Foam");
        System.out.println("   Large: PHP 490.00");
        System.out.println();

        System.out.println("3. Taro Cookie Crunch Green Tea");
        System.out.println("   Small: PHP 188.00");
        System.out.println();

        System.out.println("4. Cancel");

    }

    /**
     * Displays the main menu of the vending machine application.
     */
    public void printMainMenu() {
        clearScreen();
        System.out.println("=========================");
        System.out.println("    Vending Machine    ");
        System.out.println("=========================");
        System.out.println("1. Display Items");
        System.out.println("2. Purchase Item");
        System.out.println("3. Exit");
    }

    /**
     * Displays the menu for purchasing a randomized milk tea, explaining that the recipe, cup size, and total price will be randomly generated.
     */
    public void randomizedTeaMenu() {
        System.out.println("==========================================");
        System.out.println("           RANDOMIZED MILK TEA");
        System.out.println("==========================================");
        System.out.println("The recipe, cup size, and total");
        System.out.println("price are randomly generated.");
        System.out.println();
        System.out.println("If your inserted credit is");
        System.out.println("insufficient, the purchase");
        System.out.println("will not proceed.");
        System.out.println("==========================================");
    }

    /**
     * Displays the menu for selecting the ice level of a milk tea.
    */
    public void iceLevel(){
        System.out.println("\n===== CHOOSE ICE LEVEL =====");
        System.out.println("1. No Ice");
        System.out.println("2. Less Ice");
        System.out.println("3. Regular Ice");
        System.out.println("4. Extra Ice");
    }

    /**
    * Displays the menu for selecting the milk base of a milk tea.
    */
    public void chooseMilk() {
        System.out.println("\n===== CHOOSE MILK =====");
        System.out.println("1. Whole Milk");
        System.out.println("2. Oat Milk");
        System.out.println("3. Almond Milk");
        System.out.println("4. Skim Milk");
    }

    /**
     * Displays the menu for selecting the tea base of a milk tea.
     */
    public void chooseTea() {
        System.out.println("\n===== CHOOSE TEA BASE =====");
        System.out.println("1. Black Tea");
        System.out.println("2. Green Tea");
        System.out.println("3. Earl Grey Tea");
        System.out.println("4. Oolong Tea");
    }

    /**
    * Displays an error message indicating that the selected ingredient
    * cannot be purchased individually because it is restricted for use
    * in custom milk tea recipes only.
     */
    public void purchaseNotAllowed() {
        System.out.println("\n========== PURCHASE NOT ALLOWED ==========");
        System.out.println("The selected item is marked as (Restricted)");
        System.out.println("and cannot be purchased individually.");
        System.out.println("It is only available as an ingredient");
        System.out.println("for a custom milk tea.");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Please select another item.");
    }

    /**
     * Displays the menu for signature drinks, explaining that each signature drink has a fixed recipe, size, and price.
     */
    public void signatureDrinks() {
        System.out.println("==========================================");
        System.out.println("         SIGNATURE DRINKS");
        System.out.println("==========================================");
        System.out.println("Each signature drink has a fixed");
        System.out.println("recipe, size, and price.");
        System.out.println();
        System.out.println("Please ensure you have inserted");
        System.out.println("enough money before purchasing.");
        System.out.println("==========================================");

    }

    /**
     * Displays the menu for rating the user's experience after a purchase.
     */
    public void rateExperience() {
        System.out.println();

        System.out.println("========== RATE YOUR EXPERIENCE ==========");

        System.out.println("How would you rate your purchase?");

        System.out.println("1 - Poor");
        System.out.println("2 - Fair");
        System.out.println("3 - Good");
        System.out.println("4 - Very Good");
        System.out.println("5 - Excellent");
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

        System.out.printf("%-6s %-28s %-13s %-10s %-8s%n", "Slot", "Item", "Price", "Calories", "Stock");

        printDivider();

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

            String itemName = items[i].getName();

            // Label restricted ingredients
            if (items[i] instanceof SpecialItem) {

                SpecialItem specialItem = (SpecialItem) items[i];

                if (!specialItem.isSellableIndividually()) {
                    itemName += " *";
                }
            }

            System.out.printf("%-6d %-28s PHP%-9.2f %-10.0f %-8d%n",
                i + 1,
                itemName,
                items[i].getPrice(),
                items[i].getCalories(),
                slots[i].getCurrentInSlotItems());

            } else {

                System.out.printf("%-6d %-28s %-13s %-10s %-8d%n", i + 1, "Empty", "-", "-", 0);
                }
            }

        System.out.println();
        System.out.println("* Restricted ingredient");
        System.out.println("Available only for Custom Milk Tea.");
        printDivider();
    }

    /**
     * Prints a summary of the custom milk tea order, including the selected tea base, milk base, sweetener, sugar level, ice level, size, and any add-ons.
     *
     * @param specialMachine the SpecialVendingMachine instance used to retrieve item names
     * @param tea the index of the selected tea base in the item templates
     * @param milk the index of the selected milk base in the item templates
     * @param sweetener the index of the selected sweetener in the item templates, or -1 if no sweetener is selected
     * @param sugarLevel the selected sugar level (NO_SUGAR, HALF_SUGAR, FULL_SUGAR)
     * @param iceLevel the selected ice level (NO_ICE, LESS_ICE, REGULAR_ICE, EXTRA_ICE)
     * @param size the selected size (SMALL, MEDIUM, LARGE)
     * @param addons the list of indices of the selected add-ons in the item templates
     */
    public void printCustomMilkTeaSummary(SpecialVendingMachine specialMachine, int tea, int milk, int sweetener, int sugarLevel, int iceLevel, int size, ArrayList<Integer> addons) {
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

            System.out.println("0%");

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

        System.out.println("Add-ons:");

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

    /**
    * Displays the Special Milk Tea menu, allowing the user to create a
    * custom milk tea, purchase a signature milk tea, choose a randomized
    * milk tea, or cancel the operation.
    */
    public void printMilkTeaToBuy() {
        System.out.println("\n--- Special Milk Tea ---");
        printShortDivider();
        System.out.println("1. Create custom milk tea");
        System.out.println("2. Purchase signature milk tea");
        System.out.println("3. Randomized milk tea");
        System.out.println("4. Cancel");

    }

    /**
     * Prints a horizontal bar to the console for visual separation.
     */
    private void printDivider(){
        System.out.println("=".repeat(68));
    }


    /**
     * Prints a shorter horizontal bar to the console for visual separation.
     */
    private void printShortDivider() {
        System.out.println("=".repeat(42));
    }

    /**
    * Pauses the program until the user presses Enter.
    *
    * @param scanner shared Scanner object
    */
    public void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }


    /**
     * Prints the maintenance stock display for the vending machine, showing the current stock levels of each item in the slots.
     *
     * @param items the items in the vending machine
     * @param slots the slots in the vending machine
     */
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

    /**
    * Displays the machine's change inventory, including the quantity of
    * each denomination, low-change warnings, and the total amount of
    * change currently available.
    *
    * @param cashBox the cash box containing the machine's change inventory
    */
    public void printChangeInventory(CashBox cashBox) {

        int[] denominations = cashBox.getDenominations();
        int[] quantities = cashBox.getDenominationsAmount();

        printDivider();
        System.out.println("          CHANGE INVENTORY");
        printDivider();

        double totalCash = 0;

        for (int i = denominations.length - 1; i >= 0; i--) {

            int value = denominations[i];
            int quantity = quantities[i];

            System.out.printf("PHP %-4d : %-3d", value, quantity);

            if (quantity == 0) {

                System.out.print("   !! OUT OF CHANGE !!");

            } else {

                boolean lowStock = false;

                switch (value) {

                    case 1000:
                    case 500:
                        lowStock = quantity <= 1;
                        break;

                    case 200:
                    case 100:
                        lowStock = quantity <= 2;
                        break;

                    case 50:
                    case 20:
                        lowStock = quantity <= 3;
                        break;

                    case 10:
                    case 5:
                        lowStock = quantity <= 5;
                        break;

                    case 1:
                        lowStock = quantity <= 10;
                        break;
                    }

                if (lowStock) {
                    System.out.print("   !! LOW CHANGE !!");
                }
            }

            System.out.println();

            totalCash += value * quantity;
        }

        printDivider();
        System.out.printf("Total Change Available: PHP %.2f%n", totalCash);
        printDivider();
    }

}



