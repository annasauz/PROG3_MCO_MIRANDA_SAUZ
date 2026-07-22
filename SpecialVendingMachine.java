import java.util.ArrayList;

/**
 * Represents a Special Vending Machine capable of assembling customizable Milk Tea.
 * Inherited from RegularVendingMachine.
 */
public class SpecialVendingMachine extends RegularVendingMachine {

    /* ===========================
            SLOT CONSTANTS
    =========================== */

    // Tea Bases
    public static final int BLACK_TEA = 0;
    public static final int GREEN_TEA = 1;
    public static final int EARL_GREY_TEA = 2;
    public static final int OOLONG_TEA = 3;

    // Milk Bases
    public static final int WHOLE_MILK = 4;
    public static final int OAT_MILK = 5;
    public static final int ALMOND_MILK = 6;
    public static final int SKIM_MILK = 7;

    // Sweeteners
    public static final int HONEY = 8;
    public static final int BROWN_SUGAR_SYRUP = 9;

    // Flavor Powders
    public static final int MATCHA_POWDER = 10;
    public static final int TARO_POWDER = 11;

    // Toppings
    public static final int OREO = 12;
    public static final int CREAM_CHEESE = 13;
    public static final int TAPIOCA_PEARLS = 14;
    public static final int GLASS_JELLY = 15;
    public static final int EGG_PUDDING = 16;

    // Othersalize
    public static final int ICE = 17;

    /* ===========================
            SIZE CONSTANTS
   =========================== */

    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;

    /**
     * Initializes a Special Vending Machine.
     * Inherited from RegularVendingMachine.
     */
    public SpecialVendingMachine() {
        super(20);

        // Initializes extra custom toppings
        initializeSpecialItems();
    }


    /**
     * Loads custom milk tea toppings into slots 9 through 12.
     */
    private void initializeSpecialItems() {

    // Tea Bases
    restockSlot(BLACK_TEA,
            new SpecialItem("Black Tea", 30.00, 2, true), 10);

    restockSlot(GREEN_TEA,
            new SpecialItem("Green Tea", 38.00, 2, true), 10);

    restockSlot(EARL_GREY_TEA,
            new SpecialItem("Earl Grey Tea", 35.00, 2, true), 10);

    restockSlot(OOLONG_TEA,
            new SpecialItem("Oolong Tea", 35.00, 2, true), 10);

    // Milk Bases
    restockSlot(WHOLE_MILK,
            new SpecialItem("Whole Milk", 50.00, 150, true), 10);

    restockSlot(OAT_MILK,
            new SpecialItem("Oat Milk", 50.00, 120, true), 10);

    restockSlot(ALMOND_MILK,
            new SpecialItem("Almond Milk", 60.00, 60, true), 10);

    restockSlot(SKIM_MILK,
            new SpecialItem("Skim Milk", 45.00, 80, true), 10);

    // Sweeteners
    restockSlot(HONEY,
            new SpecialItem("Honey", 20.00, 65, true), 10);

    restockSlot(BROWN_SUGAR_SYRUP,
            new SpecialItem("Brown Sugar Syrup", 15.00, 80, false), 10);

    // Flavor Powders
    restockSlot(MATCHA_POWDER,
            new SpecialItem("Matcha Powder", 30.00, 5, true), 10);

    restockSlot(TARO_POWDER,
            new SpecialItem("Taro Powder", 30.00, 150, true), 10);

    // Toppings
    restockSlot(OREO,
            new SpecialItem("Oreo", 20.00, 160, true), 10);

    restockSlot(CREAM_CHEESE,
            new SpecialItem("Cream Cheese", 35.00, 100, false), 10);

    restockSlot(TAPIOCA_PEARLS,
            new SpecialItem("Tapioca Pearls", 20.00, 140, false), 10);

    restockSlot(GLASS_JELLY,
            new SpecialItem("Glass Jelly", 20.00, 40, false), 10);

    restockSlot(EGG_PUDDING,
            new SpecialItem("Egg Pudding", 25.00, 115, true), 10);

    // Ice
    restockSlot(ICE,
            new SpecialItem("Ice", 15.00, 0, true), 10);
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

        if (slotIndex < 0 || slotIndex >= this.slots.length) {
            return false;
        }

        Item item = this.itemTemplates[slotIndex];

        if (item == null) {
            System.out.println("No item in this slot.");
            return false;
        }

        // If it is a SpecialItem, check its property directly
        if (item instanceof SpecialItem) {
            SpecialItem specialItem = (SpecialItem) item;

            if (!specialItem.isSellableIndividually()) {
                System.out.println("Error: " + item.getName() +
                        " cannot be sold individually.");
                return false;
            }
        }

        return super.purchaseItem(slotIndex);
    }



    /**
     * Creates and dispenses a custom milk tea based on the user's selected ingredients.
     * 
     * @param teaSlot    The slot index for the chosen tea base.
     * @param milkSlot   The slot index for the chosen milk base.
     * @param addonSlots A list of slot indices for any extra toppings or syrups.
     * @return true if the transaction and preparation were successful, false otherwise.
     */
    public boolean purchaseCustomMilkTea(int teaSlot, int milkSlot, ArrayList<Integer> addonSlots, int size) {

        if (addonSlots == null || addonSlots.isEmpty()) {
            System.out.println("No add-ons selected. Please select at least one add-on.");
            return false;
        }

        // Determine ingredient multiplier based on size
        int multiplier;

        switch (size) {
            case SMALL:
                multiplier = 1;
                break;
            case MEDIUM:
                multiplier = 2;
                break;
            case LARGE:
                multiplier = 3;
                break;
            default:
                multiplier = 1;
        }

        // simple check
        if (!isValidSlot(teaSlot) || !isValidSlot(milkSlot)) {
            System.out.println("Invalid tea or milk slot selected.");
            return false;
        }

        // Ensure templates exist for tea and milk
        Item teaTemplate = this.itemTemplates[teaSlot];
        Item milkTemplate = this.itemTemplates[milkSlot];

        if (teaTemplate == null) {
            System.out.println("Selected tea slot [" + teaSlot + "] is empty.");
            return false;
        }

        if (milkTemplate == null) {
            System.out.println("Selected milk slot [" + milkSlot + "] is empty.");
            return false;
        }

        // Prevent using restricted ingredients as bases
        if (teaTemplate instanceof SpecialItem && !((SpecialItem) teaTemplate).isSellableIndividually()) {
            System.out.println("Selected tea base [" + teaTemplate.getName() + "] cannot be sold as a standalone base.");
            return false;
        }

        if (milkTemplate instanceof SpecialItem && !((SpecialItem) milkTemplate).isSellableIndividually()) {
            System.out.println("Selected milk base [" + milkTemplate.getName() + "] cannot be sold as a standalone base.");
            return false;
        }

        // Validate add-on slots
        for (int addonSlot : addonSlots) {
            if (!isValidSlot(addonSlot)) {
                System.out.println("Invalid add-on slot selected.");
                return false;
            }
        }

        // Required stock
        int[] requiredStock = new int[this.slots.length];

        requiredStock[teaSlot] += multiplier;
        requiredStock[milkSlot] += multiplier;

        // Add-ons remain one serving each
        for (int addonSlot : addonSlots) {
            requiredStock[addonSlot]++;
        }

        // Verify stock
        for (int i = 0; i < requiredStock.length; i++) {
            int needed = requiredStock[i];

            if (needed > 0) {
                if (this.slots[i].getCurrentInSlotItems() < needed) {
                    System.out.println("Insufficient stock for ingredient in slot [" + (i + 1) + "].");
                    return false;
                }
            }
        }

        // Compute total price and calories
        double totalPrice = (teaTemplate.getPrice() * multiplier) + (milkTemplate.getPrice() * multiplier);

        double totalCalories = (teaTemplate.getCalories() * multiplier) + (milkTemplate.getCalories() * multiplier);

        for (int addonSlot : addonSlots) {
            totalPrice += this.itemTemplates[addonSlot].getPrice();
            totalCalories += this.itemTemplates[addonSlot].getCalories();
        }

        // Check user money
        double userInserted = this.transactionCashBox.getMoneyAmount();

        if (userInserted < totalPrice) {
            System.out.println("Not enough money. Drink price: PHP " + totalPrice + " | You inserted: PHP " + userInserted);
            return false;
        }

        // Check if machine can make change
        double changeDue = userInserted - totalPrice;

        if (changeDue > 0 && !this.canMakeChange(changeDue)) {
            System.out.println("Transaction Failed: Machine does not have enough exact change.");
            this.produceChangeWithoutPurchase();
            return false;
        }

        // Dispense change
        if (changeDue > 0) {
            this.dispenseChange(changeDue);
        }

        // Prepare drink
        System.out.println("\n--- PREPARING CUSTOM MILK TEA ---");

        // Tea
        for (int i = 0; i < multiplier; i++) {
            Item teaInstance = this.slots[teaSlot].dispense();
            this.totalSold[teaSlot]++;

            if (i == 0) {
                System.out.println("Brewing " + teaInstance.getName());
            } else {
                System.out.println("Adding extra serving of " + teaInstance.getName());
            }

            flushLoad();
        }

        // Milk
        for (int i = 0; i < multiplier; i++) {
            Item milkInstance = this.slots[milkSlot].dispense();
            this.totalSold[milkSlot]++;

            if (i == 0) {
                System.out.println("Pouring " + milkInstance.getName());
            } else {
                System.out.println("Adding extra serving of " + milkInstance.getName());
            }

            flushLoad();
        }

        // Add-ons
        for (int addonSlot : addonSlots) {
            Item addonInstance = this.slots[addonSlot].dispense();
            this.totalSold[addonSlot]++;
            System.out.println("Adding " + addonInstance.getName());
            flushLoad();
        }

        System.out.println("Shaking and sealing cup");
        flushLoad();

        System.out.println("Milk Tea Done!");

        // Save transaction
        this.mergeTransactionToInternal();

        System.out.println("\n--- TRANSACTION COMPLETE ---");

        System.out.print("Cup Size: ");

    switch (size) {

        case SMALL:
            System.out.println("Small");
            break;

        case MEDIUM:
            System.out.println("Medium");
            break;

        case LARGE:
            System.out.println("Large");
            break;

        default:
            System.out.println("Unknown");
            break;
    }

        System.out.println("Total Price: PHP " + totalPrice);
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

    /**
     * Flushes the output stream to ensure that all buffered output is written.
     */
    private void flushLoad() {
        System.out.print("...");
        System.out.flush();
    }
}
