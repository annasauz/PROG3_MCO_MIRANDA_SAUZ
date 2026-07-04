public class TextInterface {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printCreateAndTest(){
        clearScreen();
        System.out.println("Main menu");
        System.out.println("1. Create vending machine");
        System.out.println("2. Test vending machine");
        System.out.println("3. Exit");
        System.out.print("Menu Choice: ");

    }

    public void printTestMenu(){
        clearScreen();
        System.out.println("Test menu");
        System.out.println("1. Test vending features");
        System.out.println("2. Test maintenance features");
        System.out.println("3. Exit");
        System.out.print("Menu Choice: ");

    }

    public void printVendingFeatures(){
        clearScreen();
        System.out.println("1. Display items");
        System.out.println("2. Add money");
        System.out.println("3. Purchase item");
        System.out.println("4. Return change w/o purchase");
        System.out.println("5. Exit");
        System.out.print("Vending Choice:");

    }

    public void printMaintenanceFeatures(){
        clearScreen();
        System.out.println("1. Set price");
        System.out.println("2. Set stock");
        System.out.println("3. Restock change");
        System.out.println("4. Print transaction & inventory");
        System.out.println("5. Exit");
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
     * @param items
     */
    public void printRegularVendingMachineMenu(Item[] items) {
        System.out.println("=========================");
        System.out.println(" Regular Vending Machine ");
        System.out.println("=========================");
        System.out.println("Slot  | Item  | Price  | Calories");

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                System.out.printf("%-5d | %-15s | ₱%-6.2f | %-9.2f%n",
                                  i, items[i].getName(), items[i].getPrice(), items[i].getCalories());
            } else {
                System.out.printf("%-5d | %-15s | %-6s | %-9s%n",
                                  i, "Empty", "-", "-");
            }
        }
    }
}
