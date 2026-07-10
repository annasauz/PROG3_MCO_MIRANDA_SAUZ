public class RegularVendingMachine {
    private SlotCompartment[] slots;
    private Item[] itemTemplates; 
    private CashBox internalCashBox;   
    private CashBox transactionCashBox;   
    private int[] startingInventory;
    private int[] totalSold;

    /**
     * Initializes a Regular Vending Machine.
     */
    public RegularVendingMachine() {
        this.slots = new SlotCompartment[8];
        this.itemTemplates = new Item[8];
        this.startingInventory = new int[8];
        this.totalSold = new int[8];
        this.internalCashBox = new CashBox();
        this.transactionCashBox = new CashBox();

        // Create the slot compartments
        for (int i = 0; i < 8; i++) {
            this.slots[i] = new SlotCompartment(10);
        }

        // Load default products
        initializeDefaultItems();

        // Load default change
        initializeCashBox();
    }

    /**
     * Loads the vending machine with default items.
     */
    private void initializeDefaultItems() {
        restockSlot(0, new Item("Coke", 25.00, 140), 10);
        restockSlot(1, new Item("Sprite", 25.00, 130), 10);
        restockSlot(2, new Item("Water", 20.00, 0), 10);
        restockSlot(3, new Item("Piattos", 18.00, 160), 10);
        restockSlot(4, new Item("Nova", 20.00, 170), 10);
        restockSlot(5, new Item("SkyFlakes", 15.00, 120), 10);
        restockSlot(6, new Item("Chocolate", 30.00, 210), 10);
        restockSlot(7, new Item("Cookies", 22.00, 180), 10);
    }

    /**
     * Loads the machine with initial money for producing change.
     */
    private void initializeCashBox() {
        replenishChangeReserves(1, 20);
        replenishChangeReserves(5, 20);
        replenishChangeReserves(10, 20);
        replenishChangeReserves(20, 20);
        replenishChangeReserves(50, 10);
        replenishChangeReserves(100, 10);
        replenishChangeReserves(200, 5);
        replenishChangeReserves(500, 2);
        replenishChangeReserves(1000, 1);
    }

    // ==========================================
    //        VENDING TRANSACTION FEATURES
    // ==========================================

    /**
     * Receives payment from the user in different denoms.
     * This directly updates the temporary transaction pool.
     */
    public void receivePayment(int denomination, int count) {
        this.transactionCashBox.addToCashBox(denomination, count);
        double currentTotal = this.transactionCashBox.getMoneyAmount();
        System.out.println("Inserted: PHP " + (denomination * count) + " | Total Credit: PHP " + currentTotal);
    }

    /**
     * User proceeds directly to producing change, skipping item selection.
     * Dispenses the exact physical denoms that the user fed into the machine.
     * 
     */
    public void produceChangeWithoutPurchase() {
        double amountToReturn = this.transactionCashBox.getMoneyAmount();
        boolean hasCredit = amountToReturn > 0.0;
        
        if (!hasCredit) {
            System.out.println("No credit found. Insert money first.");
        } else {
            System.out.println("\n--- Producing Change (No Purchase Made) ---");
            System.out.println("Returning your inserted denoms totaling: PHP " + amountToReturn);
            
            int[] denoms = this.transactionCashBox.getDenominations();
            int[] amounts = this.transactionCashBox.getDenominationsAmount();
            
            for (int i = 0; i < denoms.length; i++) {
                if (amounts[i] > 0) {
                    System.out.println("   Dispensed: " + amounts[i] + "x PHP " + denoms[i] + " bill/coin");
                }
            }

            // Flush the temporary transaction vault manually using a clear loop
            this.clearTransactionCashBox();
            System.out.println("Transaction canceled successfully.");
        }
    }

    /**
     * Handles the purchase of an item from a selected slot. 
     * *TRY INPUTTING CHARACTERS AND CHECK FOR ERROR/MAKE SURE TO ENFORCE THE CODE FOR THAT LATER (as invalid selection)
     */
    public boolean purchaseItem(int slotIndex) {
        // Enforce boundary checks
        if (slotIndex < 0 || slotIndex >= this.slots.length) {
            System.out.println("Invalid slot selection.");
            return false;
        }

        SlotCompartment selectedSlot = this.slots[slotIndex];
        Item itemTemplate = this.itemTemplates[slotIndex];

        // Check item availability 
        if (itemTemplate == null || selectedSlot.getCurrentInSlotItems() == 0) {
            System.out.println("Item is out of stock or unavailable.");
            return false;
        }

        double itemPrice = itemTemplate.getPrice();
        double userInserted = this.transactionCashBox.getMoneyAmount();

        // Verify user has sufficient credit
        if (userInserted < itemPrice) {
            System.out.println("Insufficient funds. Item price: PHP " + itemPrice + " | Inserted: PHP " + userInserted);
            return false;
        }

        double changeDue = userInserted - itemPrice;
      
        if (changeDue > 0) {
            boolean changeAvailable = this.canMakeChange(changeDue);
            if (!changeAvailable) {
                System.out.println("Transaction Failed: Machine cannot produce exact change. Returning your original money.");
                this.produceChangeWithoutPurchase(); // Safe refund execution
                return false;
            }
        }
     
        if (changeDue > 0) {
            this.dispenseChange(changeDue); 
        }
        
        Item dispensedItem = selectedSlot.dispense();
        this.totalSold[slotIndex] = this.totalSold[slotIndex] + 1; 
        
        this.mergeTransactionToInternal();
        
        System.out.println("\n--- VENDING SUCCESS ---");
        System.out.println("Dispensed: " + dispensedItem.getName());
        System.out.println("Calories: " + dispensedItem.getCalories() + " kcal");
        System.out.println("Total Change Dispensed: PHP " + changeDue);
        
        return true;
    }

    // ==========================================
    //           MAINTENANCE FEATURES
    // ==========================================

    /**
     * Restocks a slot with an item and captures the baseline starting capacity.
     */
    public void restockSlot(int slotIndex, Item item, int amount) {
        if (slotIndex >= 0 && slotIndex < this.slots.length) {
            this.itemTemplates[slotIndex] = item;
            this.slots[slotIndex].addInventory(item, amount);
            
            this.startingInventory[slotIndex] = this.slots[slotIndex].getCurrentInSlotItems();
            this.totalSold[slotIndex] = 0; 
        }
    }

    /**
     * Clears all items from a specific slot. 
     */
    public void clearSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < this.slots.length) {
            this.slots[slotIndex].clearItems();
        }
    }

    /**
     * Replenishes physical coin/bill counts for change.
     */
    public void replenishChangeReserves(int denomination, int amount) {
        this.internalCashBox.addToCashBox(denomination, amount);
        System.out.println("Replenished " + amount + " units of PHP " + denomination + " to the machine reserves.");
    }

    /**
     * Formats and displays starting/ending inventory counts along with revenue values.
     */
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
            } else {
                itemName = "Empty Slot";
            }
            
            int starting = this.startingInventory[i];
            int current = this.slots[i].getCurrentInSlotItems();
            int sold = this.totalSold[i];
            
            double itemRevenue = 0;
            if (item != null) {
                itemRevenue = sold * item.getPrice();
            }
            
            totalRevenue = totalRevenue + itemRevenue;

            System.out.println("Slot [" + i + "] " + itemName + ":");
            System.out.println("   • Starting Inventory: " + starting);
            System.out.println("   • Ending Inventory:   " + current);
            System.out.println("   • Total Units Sold:   " + sold);
            System.out.printf("   • Total Revenue:       PHP %.2f\n\n", itemRevenue);
        }
        System.out.println("---------------------------------------------");
        System.out.printf("TOTAL ENGINE REVENUE RECOVERED: PHP %.2f\n", totalRevenue);
        System.out.println("=============================================");
    }

    /**
     * Collects all money currently stored inside the machine and resets sales trackers.
     * inventory reset included
     */
    public void collectMoney() {
        double collectedAmount = internalCashBox.getMoneyAmount();
        System.out.printf("Collected: PHP %.2f%n", collectedAmount);

        int[] amounts = internalCashBox.getDenominationsAmount();
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = 0;
        }

        // Reset tracking for the next maintenance cycle
        for (int i = 0; i < this.slots.length; i++) {
            this.startingInventory[i] = this.slots[i].getCurrentInSlotItems();
            this.totalSold[i] = 0;
        }

        System.out.println("Machine cash box has been emptied. Please restock change reserves.");
    }


    // ==========================================
    //             INTERNAL CALCULATIONS
    // ==========================================

    /**
     * Verifies if change calculation can be fully satisfied by walking down denoms counts.
     */
    private boolean canMakeChange(double changeDue) {
        int[] denoms = this.internalCashBox.getDenominations();
        int[] amounts = this.internalCashBox.getDenominationsAmount();
        double remainingChange = changeDue;

        for (int i = denoms.length - 1; i >= 0; i--) {
            if (denoms[i] <= remainingChange) {
                if (amounts[i] > 0) {
                    int countNeeded = (int) (remainingChange / denoms[i]);
                    
                    int countToTake;
                    if (countNeeded < amounts[i]) {
                        countToTake = countNeeded;
                    } else {
                        countToTake = amounts[i];
                    }

                    remainingChange = remainingChange - (countToTake * denoms[i]);
                }
            }
        }

        // If remaining change is exactly 0, it means it can be correctly broken down
        if (remainingChange == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Dispenses change value
     */
    private void dispenseChange(double changeDue) {
        int[] denoms = this.internalCashBox.getDenominations();
        int[] amounts = this.internalCashBox.getDenominationsAmount();
        double remainingChange = changeDue;

        System.out.println("Dispensing PHP " + changeDue + " change from machine bank...");
        
        for (int i = denoms.length - 1; i >= 0; i--) {
            if (denoms[i] <= remainingChange) {
                if (amounts[i] > 0) {
                    int countNeeded = (int) (remainingChange / denoms[i]);
                    
                    int countToTake;
                    if (countNeeded < amounts[i]) {
                        countToTake = countNeeded;
                    } else {
                        countToTake = amounts[i];
                    }

                    amounts[i] = amounts[i] - countToTake;
                    remainingChange = remainingChange - (countToTake * denoms[i]);
                    
                    if (countToTake > 0) {
                        System.out.println("   Dispensing: " + countToTake + "x PHP " + denoms[i]);
                    }
                }
            }
        }
    }

    /**
     * Merges current transaction balances directly into the internal change box.
     */
    private void mergeTransactionToInternal() {
        int[] userDenoms = this.transactionCashBox.getDenominations();
        int[] userAmounts = this.transactionCashBox.getDenominationsAmount();
        
        for (int i = 0; i < userDenoms.length; i++) {
            this.internalCashBox.addToCashBox(userDenoms[i], userAmounts[i]);
        }
        this.clearTransactionCashBox();
    }

    /**
     * Clears user transaction credit arrays.
     */
    private void clearTransactionCashBox() {
        int[] amounts = this.transactionCashBox.getDenominationsAmount();
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = 0;
        }
    }

    public Item[] getItemTemplates() {
        return itemTemplates;
    }

    public SlotCompartment[] getSlots() {
        return slots;
    }
}