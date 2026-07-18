import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        super();
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
     * Validates if a slot index is within the acceptable boundaries.
     */
    private boolean isValidSlot(int slotIndex) {
        return slotIndex >= 0 && slotIndex < this.slots.length;
    }
}
