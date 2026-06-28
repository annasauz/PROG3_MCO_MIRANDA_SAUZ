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

}

public void produceChangeWithoutPurchase() {

}

public boolean purchaseItem(int slotIndex) {

    return false;
}


// ==========================================
//          MAINTENANCE FEATURES
// ==========================================

public void restockSlot(int slotIndex, Item item, int amount) {

}


public void replenishChangeReserves(int denomination, int amount) {

}

public void printTransactionSummary() {

}


// ==========================================
//     SPECIAL VENDING MACHINE FEATURES
// ==========================================

public void displayIngredientMenu() {

}

public boolean purchaseCustomProduct() {

    return false;
}

public double calculateTotalPrice(int[] ingredientChoices) {

    return 0;
}

public double calculateTotalCalories(int[] ingredientChoices) {

    return 0;
}

public boolean checkIngredientAvailability(int[] ingredientChoices) {

    return false;
}

public void deductIngredients(int[] ingredientChoices) {

}

public void prepareProduct(int[] ingredientChoices) {

}


// ==========================================
//       INTERNAL CALCULATIONS
// ==========================================

private boolean canMakeChange(double changeDue) {

    return false;
}

private void dispenseChange(double changeDue) {

}

private void mergeTransactionToInternal() {

}

private void clearTransactionCashBox() {

}

private Item getIngredient(int slotIndex) {

    return ingredientTemplates[slotIndex];
}
}