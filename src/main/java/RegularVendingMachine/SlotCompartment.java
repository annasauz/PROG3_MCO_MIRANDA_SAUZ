package src.main.java.RegularVendingMachine;

//MESSY WIP
//MESSY WIP
//MESSY WIP

public class SlotCompartment {
    private int maximumSlotItems;
    private int currentSlotItems = 0;
    private Item[] items;

    // Constructor
    /** Initializes the slot compartment with a specified item and maximum capacity.

        @param item The item to be placed in the slot compartment
        @param maxItems The maximum number of items the slot can hold (must be at least 10)
    */
    public SlotCompartment(Item item,int maxItems) {
        if(maxItems >= 10){
            this.maximumSlotItems = maxItems;
        } else {
            this.maximumSlotItems = 10;
        }

        items = new Item[maximumSlotItems];

        //initializes contents of items array
        for(int i = 0; i < items.length; i++){
            items[i] = new Item("", 0.0, 0.0);

        }
    }

}
