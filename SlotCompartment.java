import java.util.ArrayList;

public class SlotCompartment {
    private final int MAXIMUM_IN_SLOT_ITEMS;
    private final int MINIMUMIN_SLOT_ITEMS = 10;
    private int currentInSlotItems = 0;
    private ArrayList<Item> items;

    // Constructor
    /** Creates a slot for items
     *
     *  @param maxItems The maximum number of items the slot can hold (must be at least 10)
    */
    public SlotCompartment(int maxItems) {
        this.MAXIMUM_IN_SLOT_ITEMS = Math.max(maxItems, MINIMUMIN_SLOT_ITEMS);

        items = new ArrayList<>();
    }

    // Methods
    /** Validates if items arraylist is empty, if so returns nothing. Otherwise, dispenses one item
     */
    public Item dispense() {
    if (items.isEmpty()) {
        System.out.println("Out of product.");
        return null;
    }

    Item dispensedItem = items.remove(0);

    adjustCurrentInSlotItems(-1);

    return dispensedItem;
}

    /** 
     * Allows the user to add stock/inventory of a specific slot 
     * by creating distinct physical copies of the given item.
     *
     * @param item   The template item to be copied
     * @param amount integer value to be added to the current item amount
     */
    public void addInventory(Item item, int amount) {

        if (this.currentInSlotItems == this.MAXIMUM_IN_SLOT_ITEMS) {
            System.out.println("\n========== RESTOCK FAILED ==========");
            System.out.println("The selected slot is already at maximum capacity.");
            System.out.println("Current Stock : " + this.currentInSlotItems + "/" + this.MAXIMUM_IN_SLOT_ITEMS);
            System.out.println("====================================");
        }
        else if (amount <= 0) {
            System.out.println("\n========== RESTOCK FAILED ==========");
            System.out.println("Invalid quantity entered.");
            System.out.println("Please enter a positive number.");
            System.out.println("====================================");
        }
        else if (amount + this.currentInSlotItems > this.MAXIMUM_IN_SLOT_ITEMS) {
            int availableSpace = this.MAXIMUM_IN_SLOT_ITEMS - this.currentInSlotItems;

            System.out.println("\n========== RESTOCK FAILED ==========");
            System.out.println("Adding " + amount + " stock(s) would exceed the slot capacity.");
            System.out.println("Current Stock : " + this.currentInSlotItems + "/" + this.MAXIMUM_IN_SLOT_ITEMS);
            System.out.println("Available Space: " + availableSpace + " stock(s)");
            System.out.println("====================================");
        }
        else {
            
            for (int i = 0; i < amount; i++) {
                
                if (item instanceof SpecialItem) {
                    // specialItem clone
                    SpecialItem specialTemplate = (SpecialItem) item;
                    items.add(new SpecialItem(specialTemplate));
                } else {
                    // regular item clone
                    items.add(new Item(item));
                }
                
            }
            adjustCurrentInSlotItems(amount);

        }
    }

    /**
     * Updates the price of each physical item in the slot.
     * 
     * @param newPrice The new price to set
     */
    public void updatePricesInSlot(double newPrice) {        
        for (int i = 0; i < this.items.size(); i++) {
            // update price
            this.items.get(i).setPrice(newPrice);
        }
        System.out.println("All existing items in the slot updated to PHP " + newPrice);
    }

    //Getters, setters
    public int getCurrentInSlotItems() {
        return currentInSlotItems;
    }

    public int getMaximumInSlotItems() {
        return MAXIMUM_IN_SLOT_ITEMS;
    }

    public int getMinimumInSlotItems() {
        return MINIMUMIN_SLOT_ITEMS;
    }

    public void adjustCurrentInSlotItems(int addedAmount) {
    this.currentInSlotItems += addedAmount;
    }
}
