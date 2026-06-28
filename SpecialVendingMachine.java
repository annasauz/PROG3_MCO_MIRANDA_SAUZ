public class SpecialVendingMachine {

    private SlotCompartment[] slots;
    private Item[] itemTemplates;
    private CashBox internalCashBox;
    private CashBox transactionCashBox;
    private int[] startingInventory;
    private int[] totalSold;

    // Special Vending Machine
    private SlotCompartment[] ingredientSlots;
    private Item[] ingredientTemplates;


   public SpecialVendingMachine() {

    this.slots = new SlotCompartment[8];
    this.itemTemplates = new Item[8];

    this.startingInventory = new int[8];
    this.totalSold = new int[8];

    this.internalCashBox = new CashBox();
    this.transactionCashBox = new CashBox();

    // Ingredient slots
    this.ingredientSlots = new SlotCompartment[15];
    this.ingredientTemplates = new Item[15];

    for (int i = 0; i < this.slots.length; i++) {
        this.slots[i] = new SlotCompartment(10);
    }

    for (int i = 0; i < this.ingredientSlots.length; i++) {
        this.ingredientSlots[i] = new SlotCompartment(20);
    }
}

public void restockIngredient(int slotIndex, Item item, int amount) {

    if (slotIndex >= 0) {
        if (slotIndex < this.ingredientSlots.length) {

            this.ingredientTemplates[slotIndex] = item;

            this.ingredientSlots[slotIndex].addInventory(item, amount);
        }
    }
}

public void displayIngredients() {

    System.out.println("\n===== INGREDIENT INVENTORY =====");

    for (int i = 0; i < ingredientTemplates.length; i++) {

        if (ingredientTemplates[i] != null) {

            System.out.println(
                "[" + i + "] "
                + ingredientTemplates[i].getName()
                + " | Stock: "
                + ingredientSlots[i].getCurrentInSlotItems()
            );

        }

    }

}

public Item[] getIngredientTemplates() {
    return ingredientTemplates;
}

public SlotCompartment[] getIngredientSlots() {
    return ingredientSlots;
}


// ==========================================
//       VENDING TRANSACTION FEATURES
// ==========================================

public void receivePayment(int denomination, int count) {

    this.transactionCashBox.addToCashBox(denomination, count);

    double currentTotal = this.transactionCashBox.getMoneyAmount();

    System.out.println("Inserted: ₱" + (denomination * count)
            + " | Total Credit: ₱" + currentTotal);
}

public void produceChangeWithoutPurchase() {

    double amountToReturn = this.transactionCashBox.getMoneyAmount();

    if (amountToReturn == 0) {

        System.out.println("No credit found. Insert money first.");

    }
    else {

        System.out.println("\n--- Producing Change (No Purchase Made) ---");
        System.out.println("Returning your inserted denominations totaling: ₱" + amountToReturn);

        int[] denoms = this.transactionCashBox.getDenominations();
        int[] amounts = this.transactionCashBox.getDenominationsAmount();

        for (int i = 0; i < denoms.length; i++) {

            if (amounts[i] > 0) {

                System.out.println("   Dispensed: "
                        + amounts[i]
                        + "x ₱"
                        + denoms[i]
                        + " bill/coin");

            }
        }

        this.clearTransactionCashBox();

        System.out.println("Transaction canceled successfully.");
    }
}

public boolean purchaseItem(int slotIndex) {

    boolean successfulPurchase = false;

    if (slotIndex < 0 || slotIndex >= this.slots.length) {

        System.out.println("Invalid slot selection.");

    }
    else {

        SlotCompartment selectedSlot = this.slots[slotIndex];
        Item itemTemplate = this.itemTemplates[slotIndex];

        if (itemTemplate == null) {

            System.out.println("Item is out of stock or unavailable.");

        }
        else if (selectedSlot.getCurrentInSlotItems() == 0) {

            System.out.println("Item is out of stock or unavailable.");

        }
        else {

            double itemPrice = itemTemplate.getPrice();
            double userInserted = this.transactionCashBox.getMoneyAmount();

            if (userInserted < itemPrice) {

                System.out.println("Insufficient funds. Item price: ₱"
                        + itemPrice
                        + " | Inserted: ₱"
                        + userInserted);

            }
            else {

                double changeDue = userInserted - itemPrice;

                if (changeDue > 0) {

                    if (this.canMakeChange(changeDue) == false) {

                        System.out.println("Transaction Failed: Machine cannot produce exact change.");
                        this.produceChangeWithoutPurchase();

                    }
                    else {

                        this.dispenseChange(changeDue);

                        Item dispensedItem = selectedSlot.dispense();

                        this.totalSold[slotIndex]++;

                        this.mergeTransactionToInternal();

                        System.out.println("\n--- VENDING SUCCESS ---");
                        System.out.println("Dispensed: " + dispensedItem.getName());
                        System.out.println("Calories: " + dispensedItem.getCalories() + " kcal");
                        System.out.println("Total Change Dispensed: ₱" + changeDue);

                        successfulPurchase = true;
                    }

                }
                else {

                    Item dispensedItem = selectedSlot.dispense();

                    this.totalSold[slotIndex]++;

                    this.mergeTransactionToInternal();

                    System.out.println("\n--- VENDING SUCCESS ---");
                    System.out.println("Dispensed: " + dispensedItem.getName());
                    System.out.println("Calories: " + dispensedItem.getCalories() + " kcal");
                    System.out.println("Total Change Dispensed: ₱0.0");

                    successfulPurchase = true;
                }
            }
        }
    }

    return successfulPurchase;
}


// ==========================================
//          MAINTENANCE FEATURES
// ==========================================

public void restockSlot(int slotIndex, Item item, int amount) {

    if (slotIndex >= 0) {

        if (slotIndex < this.slots.length) {

            this.itemTemplates[slotIndex] = item;

            this.slots[slotIndex].addInventory(item, amount);

            this.startingInventory[slotIndex] =
                    this.slots[slotIndex].getCurrentInSlotItems();

            this.totalSold[slotIndex] = 0;
        }
    }
}

public void replenishChangeReserves(int denomination, int amount) {

    this.internalCashBox.addToCashBox(denomination, amount);

    System.out.println("Replenished "
            + amount
            + " units of ₱"
            + denomination
            + " to the machine reserves.");
}

public void printTransactionSummary() {

    System.out.println("\n=============================================");
    System.out.println("         VENDING TRANSACTION SUMMARY         ");
    System.out.println("=============================================");

    double totalRevenue = 0;

    for (int i = 0; i < this.slots.length; i++) {

        Item item = this.itemTemplates[i];

        String itemName;

        if (item != null) {
            itemName = item.getName();
        }
        else {
            itemName = "Empty Slot";
        }

        int starting = this.startingInventory[i];
        int current = this.slots[i].getCurrentInSlotItems();
        int sold = this.totalSold[i];

        double itemRevenue = 0;

        if (item != null) {
            itemRevenue = sold * item.getPrice();
        }

        totalRevenue += itemRevenue;

        System.out.println("Slot [" + i + "] " + itemName + ":");
        System.out.println("   • Starting Inventory: " + starting);
        System.out.println("   • Ending Inventory:   " + current);
        System.out.println("   • Total Units Sold:   " + sold);
        System.out.printf("   • Total Revenue:      ₱%.2f%n%n", itemRevenue);
    }

    System.out.println("---------------------------------------------");
    System.out.printf("TOTAL ENGINE REVENUE RECOVERED: ₱%.2f%n", totalRevenue);
    System.out.println("=============================================");
}


// ==========================================
//     SPECIAL VENDING MACHINE FEATURES
// ==========================================

public void displayIngredientMenu() {

    System.out.println("\n========== INGREDIENT MENU ==========");

    for (int i = 0; i < this.ingredientTemplates.length; i++) {

        if (this.ingredientTemplates[i] != null) {

            System.out.println(
                "[" + i + "] "
                + this.ingredientTemplates[i].getName()
                + " | ₱"
                + this.ingredientTemplates[i].getPrice()
                + " | "
                + this.ingredientTemplates[i].getCalories()
                + " kcal"
            );

        }
    }

    System.out.println("=====================================");
}

public boolean purchaseCustomProduct(int[] ingredientChoices) {

    boolean successfulPurchase = false;

    if (checkIngredientAvailability(ingredientChoices)) {

        double totalPrice = calculateTotalPrice(ingredientChoices);
        double totalCalories = calculateTotalCalories(ingredientChoices);
        double moneyInserted = this.transactionCashBox.getMoneyAmount();

        if (moneyInserted >= totalPrice) {

            double changeDue = moneyInserted - totalPrice;

            if (changeDue > 0) {

                if (canMakeChange(changeDue)) {

                    deductIngredients(ingredientChoices);
                    prepareProduct(ingredientChoices);

                    dispenseChange(changeDue);
                    mergeTransactionToInternal();

                    System.out.println("Total Price: ₱" + totalPrice);
                    System.out.println("Total Calories: " + totalCalories);

                    successfulPurchase = true;
                }
                else {
                    System.out.println("Machine cannot provide exact change.");
                }

            }
            else {

                deductIngredients(ingredientChoices);
                prepareProduct(ingredientChoices);

                mergeTransactionToInternal();

                System.out.println("Total Price: ₱" + totalPrice);
                System.out.println("Total Calories: " + totalCalories);

                successfulPurchase = true;
            }

        }
        else {
            System.out.println("Insufficient payment.");
        }

    }
    else {
        System.out.println("One or more ingredients are unavailable.");
    }

    return successfulPurchase;
}

public double calculateTotalPrice(int[] ingredientChoices) {

    double totalPrice = 0;

    for (int i = 0; i < ingredientChoices.length; i++) {

        int slot = ingredientChoices[i];

        if (slot >= 0 && slot < this.ingredientTemplates.length) {

            totalPrice += this.ingredientTemplates[slot].getPrice();

        }
    }

    return totalPrice;
}

public double calculateTotalCalories(int[] ingredientChoices) {

    double totalCalories = 0;

    for (int i = 0; i < ingredientChoices.length; i++) {

        int slot = ingredientChoices[i];

        if (slot >= 0 && slot < this.ingredientTemplates.length) {

            totalCalories += this.ingredientTemplates[slot].getCalories();

        }
    }

    return totalCalories;
}

public boolean checkIngredientAvailability(int[] ingredientChoices) {

    boolean available = true;

    for (int i = 0; i < ingredientChoices.length; i++) {

        int slot = ingredientChoices[i];

        if (slot >= 0 && slot < this.ingredientSlots.length) {

            if (this.ingredientSlots[slot].getCurrentInSlotItems() == 0) {

                System.out.println(
                    this.ingredientTemplates[slot].getName()
                    + " is out of stock."
                );

                available = false;
            }
        }
    }

    return available;
}

public void deductIngredients(int[] ingredientChoices) {

    for (int i = 0; i < ingredientChoices.length; i++) {

        int slot = ingredientChoices[i];

        if (slot >= 0 && slot < this.ingredientSlots.length) {

            this.ingredientSlots[slot].dispense();

        }
    }
}

public void prepareProduct(int[] ingredientChoices) {

   // code
}


// ==========================================
//       INTERNAL CALCULATIONS
// ==========================================

private boolean canMakeChange(double changeDue) {

    int[] denoms = this.internalCashBox.getDenominations();
    int[] amounts = this.internalCashBox.getDenominationsAmount();

    double remainingChange = changeDue;

    for (int i = denoms.length - 1; i >= 0; i--) {

        if (denoms[i] <= remainingChange) {

            if (amounts[i] > 0) {

                int countNeeded = (int)(remainingChange / denoms[i]);

                int countToTake;

                if (countNeeded < amounts[i]) {
                    countToTake = countNeeded;
                }
                else {
                    countToTake = amounts[i];
                }

                remainingChange = remainingChange - (countToTake * denoms[i]);
            }
        }
    }

    if (remainingChange == 0) {
        return true;
    }
    else {
        return false;
    }
}

private void dispenseChange(double changeDue) {

    int[] denoms = this.internalCashBox.getDenominations();
    int[] amounts = this.internalCashBox.getDenominationsAmount();

    double remainingChange = changeDue;

    System.out.println("Dispensing ₱" + changeDue + " change from machine bank...");

    for (int i = denoms.length - 1; i >= 0; i--) {

        if (denoms[i] <= remainingChange) {

            if (amounts[i] > 0) {

                int countNeeded = (int)(remainingChange / denoms[i]);

                int countToTake;

                if (countNeeded < amounts[i]) {
                    countToTake = countNeeded;
                }
                else {
                    countToTake = amounts[i];
                }

                amounts[i] = amounts[i] - countToTake;

                remainingChange = remainingChange - (countToTake * denoms[i]);

                if (countToTake > 0) {
                    System.out.println("   Dispensing: "
                            + countToTake
                            + "x ₱"
                            + denoms[i]);
                }
            }
        }
    }
}

private void mergeTransactionToInternal() {

    int[] userDenoms = this.transactionCashBox.getDenominations();
    int[] userAmounts = this.transactionCashBox.getDenominationsAmount();

    for (int i = 0; i < userDenoms.length; i++) {
        this.internalCashBox.addToCashBox(userDenoms[i], userAmounts[i]);
    }

    this.clearTransactionCashBox();
}

private void clearTransactionCashBox() {

    int[] amounts = this.transactionCashBox.getDenominationsAmount();

    for (int i = 0; i < amounts.length; i++) {
        amounts[i] = 0;
    }
}

private Item getIngredient(int slotIndex) {

    return ingredientTemplates[slotIndex];
}



}
