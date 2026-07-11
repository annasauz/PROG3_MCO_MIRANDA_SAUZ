/**
 * Represents a Regular Vending Machine that handles items, inventory, transactions, and change dispensing.
 */
public class RegularVendingMachine {
    private SlotCompartment[] slots;
    private Item[] itemTemplates; 
    private CashBox internalCashBox;   
    private CashBox transactionCashBox;   
    private int[] startingInventory;
    private int[] totalSold;

    /**
     * Initializes a Regular Vending Machine.
     * Precondition: None.
     * Postcondition: Slots, inventory arrays, and cash boxes are instantiated and populated with default data.
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
     * Precondition: Slot arrays must be initialized.
     * Postcondition: Each slot is populated with a default item and a stock of 10.
     */
    private void initializeDefaultItems() {
        restockSlot(0, new Item("Green Tea", 30.00, 2), 10);
        restockSlot(1, new Item("Black Tea", 38.00, 2), 10);
        restockSlot(2, new Item("Oatmilk", 50.00, 120), 10);
        restockSlot(3, new Item("Whole Milk", 50.00, 150), 10);
        restockSlot(4, new Item("Honey", 20.00, 65), 10);
        restockSlot(5, new Item("Matcha Powder", 30.00, 5), 10);
        restockSlot(6, new Item("Oreo", 20.00, 160), 10);
        restockSlot(7, new Item("Egg Pudding", 25.00, 115), 10);
    }

    /**
     * Loads the machine with initial money for producing change.
     * Precondition: Internal cash box must be initialized.
     * Postcondition: The machine has a starting reserve of various Philippine Peso denominations.
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
     * Receives payment from the user in different denominations and updates the temporary transaction pool.
     * Precondition: Denomination should be valid and count should be positive.
     * Postcondition: The transactionCashBox's money amount increases by the inserted denomination * count.
     * * @param denomination The face value of the inserted bill/coin.
     * @param count The quantity of the inserted bill/coin.
     */
    public void receivePayment(int denomination, int count) {
        this.transactionCashBox.addToCashBox(denomination, count);
        double currentTotal = this.transactionCashBox.getMoneyAmount();
        System.out.println("Inserted: PHP " + (denomination * count) + " | Total Credit: PHP " + currentTotal);
    }

    /**
     * User proceeds directly to producing change, skipping item selection.
     * Dispenses the exact physical denominations that the user fed into the machine.
     * Precondition: None.
     * Postcondition: The transactionCashBox is completely emptied and credits return to zero.
     */
    public void produceChangeWithoutPurchase() {
        double amountToReturn = this.transactionCashBox.getMoneyAmount();
        boolean hasCredit = amountToReturn > 0.0;
        
        if (!hasCredit) {
            System.out.println("No credit found.");
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

            this.clearTransactionCashBox();
            System.out.println("Transaction canceled successfully.");
        }
    }

    /**
     * Handles the purchase of an item from a selected slot. 
     * Precondition: The slotIndex must be within bounds, the item must be in stock, and the user must have inserted sufficient funds.
     * Postcondition: If successful, an item is dispensed, change is calculated/returned, transaction money is merged to internal reserves, and inventory/sales are updated.
     * * @param slotIndex The index of the slot the user wishes to purchase from.
     * @return true if the purchase and dispensing were successful, false otherwise.
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

        if (dispensedItem == null) {
            System.out.println("Error: Item could not be dispensed.");
            return false;
        }

        this.mergeTransactionToInternal();
        
        System.out.println("\n--- VENDING SUCCESS ---");
        System.out.println("Dispensed: " + dispensedItem.getName());
        System.out.println("Calories: " + dispensedItem.getCalories() + " kcal");
        System.out.println("Total Change Dispensed: PHP " + changeDue);
        
        return true;
    }

    // ==========================================
    //            MAINTENANCE FEATURES
    // ==========================================

    /**
     * Restocks a slot with an item and captures the baseline starting capacity.
     * Precondition: The slotIndex must be within bounds, and amount should be greater than zero.
     * Postcondition: The targeted slot contains the new stock, starting inventory is recorded, and total sold resets to 0.
     * @param slotIndex The index of the slot to restock.
     * @param item The Item object to be placed inside the slot.
     * @param amount The quantity of items to add.
     */
    public void restockSlot(int slotIndex, Item item, int amount) {
        boolean isValidSlot = slotIndex >= 0 && slotIndex < this.slots.length;
        boolean isValidAmount = amount > 0;
        boolean isItemNotNull = item != null;

        if (!isItemNotNull) {
            System.out.println("Invalid item. Please provide a valid item to restock.");
            return;
        }

        if (!isValidSlot) {
            System.out.println("Invalid slot index. Must be between 0 and " + (this.slots.length - 1));
            return;
        }

        if (!isValidAmount) {
            System.out.println("Invalid restock amount. Must be a positive integer.");
            return;
        }

        if (isValidSlot) {
            this.itemTemplates[slotIndex] = item;
        }

        if (this.itemTemplates[slotIndex].getName().equals(item.getName())) {

            this.slots[slotIndex].addInventory(this.itemTemplates[slotIndex], amount);

            this.startingInventory[slotIndex] = this.slots[slotIndex].getCurrentInSlotItems();
            this.totalSold[slotIndex] = 0;

        } else {
            System.out.println("This slot is permanently assigned to "
                    + this.itemTemplates[slotIndex].getName()
                    + ". Only this item may be restocked.");
        }
    }    


    /**
     * Replenishes physical coin/bill counts for change reserves.
     * Precondition: Denomination must be a valid face value and amount must be positive.
     * Postcondition: The internalCashBox increments its stock of the specified denomination.
     * @param denomination The face value of the bill/coin.
     * @param amount The quantity to add to the reserves.
     */
    public void replenishChangeReserves(int denomination, int amount) {
        this.internalCashBox.addToCashBox(denomination, amount);
        System.out.println("Replenished " + amount + " units of PHP " + denomination + " to the machine reserves.");
    }

    /**
     * Formats and displays starting/ending inventory counts along with revenue values.
     * Precondition: None.
     * Postcondition: A full formatted transaction summary is printed to the console.
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
     * Precondition: None.
     * Postcondition: Internal cash box amounts become 0, sales trackers are reset to 0, and starting inventory updates to the current amount.
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
     * Verifies if change calculation can be fully satisfied by walking down denomination counts.
     * Precondition: changeDue must be a positive number.
     * Postcondition: Returns a boolean indicating if the machine has exact physical denominations to make up the changeDue.
     * * @param changeDue The amount of change required to be returned to the user.
     * @return true if the machine has sufficient exact change, false otherwise.
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
        //math abs and less than 0.01 was used to account for floating point error and turning negative to positive change
        if (Math.abs(remainingChange) < 0.01) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Dispenses change value by physically reducing the internal cash reserves.
     * Precondition: canMakeChange(changeDue) must evaluate to true prior to calling this method.
     * Postcondition: Internal reserves are decremented by the exact denominations used to make change.
     * * @param changeDue The amount of change required to be returned to the user.
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
     * Precondition: A successful purchase must have just occurred.
     * Postcondition: Temporary transaction funds are absorbed into the internalCashBox and the transactionCashBox is cleared.
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
     * Precondition: None.
     * Postcondition: All denomination quantities inside the transactionCashBox are set to 0.
     */
    private void clearTransactionCashBox() {
        int[] amounts = this.transactionCashBox.getDenominationsAmount();
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = 0;
        }
    }

    /**
     * Returns the item templates for external inspection.
     * Precondition: None.
     * Postcondition: Returns the array of item templates currently tracked by the machine.
     * * @return Array of Item objects representing the templates.
     */
    public Item[] getItemTemplates() {
        return itemTemplates;
    }

    /**
     * Returns the slot compartments for external inspection.
     * Precondition: None.
     * Postcondition: Returns the array of slot compartments currently inside the machine.
     * * @return Array of SlotCompartment objects.
     */
    public SlotCompartment[] getSlots() {
        return slots;
    }
}
