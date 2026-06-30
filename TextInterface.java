public class TextInterface {
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
