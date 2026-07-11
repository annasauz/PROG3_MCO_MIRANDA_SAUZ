import java.util.ArrayList;

/**
 * Represents a compartment in a vending machine that holds a specific type of item.
 */
public class SlotCompartment {
    private final int maximumInSlotItems;
    private final int minimumInSlotItems = 10;
    private int currentInSlotItems = 0;
    private ArrayList<Item> items;

    // Constructor
    /** Creates a slot for items
     *
     *  @param maxItems The maximum number of items the slot can hold (must be at least 10)
    */
    public SlotCompartment(int maxItems) {
        this.maximumInSlotItems = Math.max(maxItems, minimumInSlotItems);

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

    /** Allows the user add stock/inventory of a specific slot
     *
     * @param amount integer value to be added to the current item amount
     */
   public void addInventory(Item item, int amount) {

    if (this.currentInSlotItems == this.maximumInSlotItems) {
        System.out.println("At maximum capacity");
    }
    else if (amount <= 0) {
        System.out.println("Invalid number");
    }
    else if (amount + this.currentInSlotItems > this.maximumInSlotItems) {
        System.out.println("At overflowing capacity");
    }
    else {
        for (int i = 0; i < amount; i++) {
            items.add(item);
        }
        adjustCurrentInSlotItems(amount);
        System.out.println("Stock added successfully.");
    }
}


    //Getters, setters
    public int getCurrentInSlotItems() {
        return currentInSlotItems;
    }

    public int getMaximumInSlotItems() {
        return maximumInSlotItems;
    }

    public int getMinimumInSlotItems() {
        return minimumInSlotItems;
    }

    public void adjustCurrentInSlotItems(int addedAmount) {
    this.currentInSlotItems += addedAmount;
    }
}
