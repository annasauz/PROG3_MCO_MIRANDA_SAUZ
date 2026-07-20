import java.util.ArrayList;

/**
 * Represents a Special Vending Machine capable of assembling customizable Milk Tea.
 * Inherited from RegularVendingMachine.
 */
public class SpecialVendingMachine extends RegularVendingMachine {

    /**
     * Initializes a Special Vending Machine.
     * Inherited from RegularVendingMachine.
     */
    public SpecialVendingMachine() {
        super(12);

        // Initializes extra custom toppings
        initializeSpecialItems();
    }


    /**
     * Loads custom milk tea toppings into slots 9 through 12.
     */
    private void initializeSpecialItems() {
        // For empty slots 8-11
        restockSlot(8, new SpecialItem("Brown Sugar Syrup", 15.00, 80, false), 10);
        restockSlot(9, new SpecialItem("Cream Cheese", 35.00, 100, false), 10);
        restockSlot(10, new SpecialItem("Tapioca Pearls", 20.00, 140, false), 10);
        restockSlot(11, new SpecialItem("Glass Jelly", 20.00, 40, false), 10);
    }
    /**
     * Overrides the regular purchase method to prevent the user from buying 
     * restricted ingredients (like toppings or syrups) individually.
     *
     * @param slotIndex The index of the slot the user wishes to purchase from.
     * @return true if the purchase was successful, false otherwise.
     */
    @Override
    public boolean purchaseItem(int slotIndex) {
        // Boundary check handled by parent, but we need to check the item name first
        if (slotIndex >= 0 && slotIndex < this.slots.length) {
            Item item = this.getItemTemplates()[slotIndex];
            
            if (item != null && !isSellableIndividually(item.getName())) {
                System.out.println("Error: " + item.getName() + " cannot be sold individually.");
                return false;
            }
        }
        
        // If it is sellable, proceed 
        return super.purchaseItem(slotIndex);
    }

    /**
     * Helper method to determine if an item is allowed to be sold on its own.
     * 
     * @param itemName The name of the item.
     * @return true if it can be sold individually, false if it is a restricted add-on.
     */
    private boolean isSellableIndividually(String itemName) {
        String[] restrictedItems = {
            "Brown Sugar Syrup", 
            "Cream Cheese", 
            "Tapioca Pearls", 
            "Glass Jelly"
        };
        
        for (String restricted : restrictedItems) {
            if (restricted.equalsIgnoreCase(itemName)) {
                return false;
            }
        }
        return true;
    }



    /**
     * Creates and dispenses a custom milk tea based on the user's selected ingredients.
     * 
     * @param teaSlot    The slot index for the chosen tea base.
     * @param milkSlot   The slot index for the chosen milk base.
     * @param addonSlots A list of slot indices for any extra toppings or syrups.
     * @return true if the transaction and preparation were successful, false otherwise.
     */
    public boolean purchaseCustomMilkTea(int teaSlot, int milkSlot, ArrayList<Integer> addonSlots) {
        
        // simple check
        if (!isValidSlot(teaSlot) || !isValidSlot(milkSlot)) {
            System.out.println("Invalid tea or milk slot selected.");
            return false;
        }
        
        for (int addonSlot : addonSlots) {
            if (!isValidSlot(addonSlot)) {
                System.out.println("Invalid add-on slot selected.");
                return false;
            }
        }

        // new array
        int[] requiredStock = new int[this.slots.length];
        
       
        requiredStock[teaSlot]++;
        
       
        requiredStock[milkSlot]++;
        
        // this loop just copies
        for (int addonSlot : addonSlots) {
            requiredStock[addonSlot]++;
        }

        // Verify stock against physical instances
        for (int i = 0; i < requiredStock.length; i++) {
            int needed = requiredStock[i];
            
            // if item is needed, check stock
            if (needed > 0) {
                if (this.slots[i].getCurrentInSlotItems() < needed) {
                    System.out.println("Insufficient stock for ingredient in slot [" + i + "].");
                    return false;
                }
            }
        }

        // add up total price and calories
        double totalPrice = this.itemTemplates[teaSlot].getPrice() + this.itemTemplates[milkSlot].getPrice();
        double totalCalories = this.itemTemplates[teaSlot].getCalories() + this.itemTemplates[milkSlot].getCalories();
        
        for (int addonSlot : addonSlots) {
            totalPrice += this.itemTemplates[addonSlot].getPrice();
            totalCalories += this.itemTemplates[addonSlot].getCalories();
        }

        // sufficient money check
        double userInserted = this.transactionCashBox.getMoneyAmount();
        if (userInserted < totalPrice) {
            System.out.println("Not enough money. Drink price: PHP " + totalPrice + " | You inserted: PHP " + userInserted);
            return false;
        }

        // if machine has change
        double changeDue = userInserted - totalPrice;
        if (changeDue > 0 && !this.canMakeChange(changeDue)) {
            System.out.println("Transaction Failed: Machine does not have enough exact change.");
            this.produceChangeWithoutPurchase(); // Refund their inserted money safely
            return false;
        }

        // dispense change
        if (changeDue > 0) {
            this.dispenseChange(changeDue);
        }

        // prints and dispensing items
        System.out.println("\n--- PREPARING CUSTOM MILK TEA ---");
        
        Item teaInstance = this.slots[teaSlot].dispense();
        this.totalSold[teaSlot]++;
        System.out.println("Brewing " + teaInstance.getName() + "...");
        
        Item milkInstance = this.slots[milkSlot].dispense();
        this.totalSold[milkSlot]++;
        System.out.println("Pouring " + milkInstance.getName() + "...");
        
        for (int addonSlot : addonSlots) {
            Item addonInstance = this.slots[addonSlot].dispense();
            this.totalSold[addonSlot]++;
            System.out.println("Adding " + addonInstance.getName() + "...");
        }

        System.out.println("Shaking and sealing cup...");
        System.out.println("Milk Tea Done!");
        
        // save money to bank to finalize it
        this.mergeTransactionToInternal();

        System.out.println("\n--- TRANSACTION COMPLETE ---");
        System.out.println("Total Calories: " + totalCalories + " kcal");
        System.out.println("Total Change Dispensed: PHP " + changeDue);

        return true;
    }

    /**
     * Validates if a slot index is within the acceptable boundaries.
     */
    private boolean isValidSlot(int slotIndex) {
        return slotIndex >= 0 && slotIndex < this.slots.length;
    }
}
