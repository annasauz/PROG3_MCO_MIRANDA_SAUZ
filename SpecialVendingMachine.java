import java.util.ArrayList;

/**
 * Represents a Special Vending Machine capable of assembling customizable Milk Tea.
 * Inherited from RegularVendingMachine.
 */
public class SpecialVendingMachine extends RegularVendingMachine {

    private int customMilkTeaSold;
    private double customMilkTeaRevenue;


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

    // Others
    public static final int ICE = 17;

    /* ===========================
            SIZE CONSTANTS
   =========================== */

    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;

    /* ===========================
            SUGAR CONSTANTS
    =========================== */

    public static final int NO_SUGAR = 0;
    public static final int HALF_SUGAR = 1;
    public static final int FULL_SUGAR = 2;

       /* ===========================
                ICE CONSTANTS
        =========================== */

    public static final int NO_ICE = 0;
    public static final int LESS_ICE = 1;
    public static final int REGULAR_ICE = 2;
    public static final int EXTRA_ICE = 3;

    /**
     * Initializes a Special Vending Machine.
     * Inherited from RegularVendingMachine.
     */
    public SpecialVendingMachine() {
        super(18);

        // Initializes extra custom toppings
        initializeSpecialItems();

        customMilkTeaSold = 0;
        customMilkTeaRevenue = 0;


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
    public boolean purchaseCustomMilkTea(int teaSlot, int milkSlot, int sweetenerSlot, int sugarLevel, ArrayList<Integer> addonSlots, int iceLevel, int size) {

        if (addonSlots == null) {
            addonSlots = new ArrayList<>();
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

        int sweetenerServings;

        switch (sugarLevel) {

            case NO_SUGAR:
            sweetenerServings = 0;
            break;

            case HALF_SUGAR:
            sweetenerServings = (multiplier + 1) / 2;
            break;

            case FULL_SUGAR:
            default:
            sweetenerServings = multiplier;
            break;
        }

        // Determine ice servings
        int iceServings;

        switch (iceLevel) {

        case NO_ICE:
            iceServings = 0;
            break;

        case LESS_ICE:
            iceServings = 1;
            break;

        case REGULAR_ICE:
            iceServings = 2;
            break;

        default:
            iceServings = 3;
            break;
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
        requiredStock[ICE] += iceServings;
        if (sweetenerSlot != -1) {
            requiredStock[sweetenerSlot] += sweetenerServings;
        }

        // Add-ons remain one serving each
        for (int addonSlot : addonSlots) {
            requiredStock[addonSlot] += multiplier;
        }

        // Verify stock
        for (int i = 0; i < requiredStock.length; i++) {

            int needed = requiredStock[i];

            if (needed > 0) {

                if (this.slots[i].getCurrentInSlotItems() < needed) {

                    String ingredientName = this.itemTemplates[i].getName();

                    System.out.println("\nTransaction Failed.");
                    System.out.println("Not enough stock for: " + ingredientName);
                    System.out.println("Needed: " + needed);
                    System.out.println("Available: " + this.slots[i].getCurrentInSlotItems());

                    return false;
                }
            }
        }
        // Compute total price and calories
        double totalPrice = (teaTemplate.getPrice() * multiplier) + (milkTemplate.getPrice() * multiplier);

        double totalCalories = (teaTemplate.getCalories() * multiplier) + (milkTemplate.getCalories() * multiplier);

        // Sweetener
        if (sweetenerSlot != -1) {

            totalPrice += this.itemTemplates[sweetenerSlot].getPrice() * sweetenerServings;

            totalCalories += this.itemTemplates[sweetenerSlot].getCalories() * sweetenerServings;
            }

        // Add-ons (scale with cup size)
        for (int addonSlot : addonSlots) {

            totalPrice += this.itemTemplates[addonSlot].getPrice() * multiplier;

            totalCalories += this.itemTemplates[addonSlot].getCalories() * multiplier;
            }

        // Ice (price only, 0 calories)
        totalPrice += this.itemTemplates[ICE].getPrice() * iceServings;

        // Check user money
        double userInserted = this.transactionCashBox.getMoneyAmount();

        if (userInserted < totalPrice) {
             System.out.println("\n========== TRANSACTION FAILED ==========");
            System.out.println("Error: Insufficient Funds!");
            System.out.printf("Custom Milk Tea Price: PHP %.2f%n", totalPrice);
            System.out.printf("Amount Inserted:       PHP %.2f%n", userInserted);
            System.out.printf("Missing Amount:        PHP %.2f%n", (totalPrice - userInserted));
            System.out.println("Please add more money or cancel your transaction.");
            System.out.println("========================================");
            return false;
        }

        // Check if machine can make change
        double changeDue = userInserted - totalPrice;

        if (changeDue > 0 && !this.canMakeChange(changeDue)) {
            System.out.println("\n========== TRANSACTION FAILED ==========");
            System.out.println("Error: Exact Change Unavailable!");
            System.out.printf("Change Due: PHP %.2f%n", changeDue);
            System.out.println("The machine does not have the exact physical denominations");
            System.out.println("to dispense your change.");
            System.out.println("\nRefunding your inserted money...");
            System.out.println("========================================");
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
            this.revenuePerItem[teaSlot] += teaInstance.getPrice();

            String teaName = teaTemplate.getName();
            if (i == 0) {
                System.out.println("Brewing " + teaName);
            } else {
                System.out.println("Adding extra serving of " + teaName);
            }

            flushLoad();
        }

        // Milk
        for (int i = 0; i < multiplier; i++) {
            Item milkInstance = this.slots[milkSlot].dispense();
            this.totalSold[milkSlot]++;
            this.revenuePerItem[milkSlot] += milkInstance.getPrice();

            String milkName = milkTemplate.getName();
            if (i == 0) {
                System.out.println("Pouring " + milkName);
            } else {
                System.out.println("Adding extra serving of " + milkName);
            }

            flushLoad();
        }

        // Sweetener
        if (sweetenerSlot != -1) {

            String sugarText;

            switch (sugarLevel) {

                case NO_SUGAR:
                    sugarText = "0%";
                    break;

                case HALF_SUGAR:
                    sugarText = "50%";
                    break;

                case FULL_SUGAR:
                    default:
                    sugarText = "100%";
                    break;
            }

        for (int i = 0; i < sweetenerServings; i++) {

            Item sweetener = this.slots[sweetenerSlot].dispense();
            this.totalSold[sweetenerSlot]++;
            this.revenuePerItem[sweetenerSlot] += sweetener.getPrice();

            if (i == 0) {
                System.out.println("Adding " + sweetener.getName() + " (" + sugarText + ")");
            } else {
                System.out.println("Adding extra serving of " + sweetener.getName());
            }

            flushLoad();
        }
    }

        // Add-ons
        for (int addonSlot : addonSlots) {

            for (int i = 0; i < multiplier; i++) {

                Item addonInstance = this.slots[addonSlot].dispense();
                this.totalSold[addonSlot]++;
                this.revenuePerItem[addonSlot] += addonInstance.getPrice();

                if (i == 0) {
                    System.out.println("Adding " + addonInstance.getName());
                } else {
                System.out.println("Adding extra serving of " + addonInstance.getName());
                }

            flushLoad();
        }
    }

        // Ice
        if (iceServings > 0) {

            for (int i = 0; i < iceServings; i++) {
                Item ice = this.slots[ICE].dispense();
                this.totalSold[ICE]++;
                this.revenuePerItem[ICE] += ice.getPrice();
            }

            switch (iceLevel) {
            case NO_ICE:
                break;

            case LESS_ICE:
                System.out.println("Adding Less Ice");
                flushLoad();
                break;

            case REGULAR_ICE:
                System.out.println("Adding Regular Ice");
                flushLoad();
                break;

            case EXTRA_ICE:
                System.out.println("Adding Extra Ice");
                flushLoad();
                break;
        }
    }

        System.out.println("Shaking and sealing cup");
        flushLoad();

        System.out.println("Milk Tea Done!");

        // ================= RECEIPT DATA =================

        ArrayList<String> receiptItems = new ArrayList<>();

        // Tea
        for (int i = 0; i < multiplier; i++) {
            receiptItems.add(teaTemplate.getName());
        }

        // Milk
        for (int i = 0; i < multiplier; i++) {
            receiptItems.add(milkTemplate.getName());
        }

        // Sweetener
        if (sweetenerSlot != -1) {
            for (int i = 0; i < sweetenerServings; i++) {
            receiptItems.add(this.itemTemplates[sweetenerSlot].getName());
            }
        }

        // Add-ons
        for (int addonSlot : addonSlots) {
            for (int i = 0; i < multiplier; i++) {
                receiptItems.add(this.itemTemplates[addonSlot].getName());
            }
        }

        // Ice
        for (int i = 0; i < iceServings; i++) {
            receiptItems.add(this.itemTemplates[ICE].getName());
        }

        String drinkName = switch (size) {
            case SMALL -> "Small Custom Milk Tea";
            case MEDIUM -> "Medium Custom Milk Tea";
            case LARGE -> "Large Custom Milk Tea";
            default -> "Custom Milk Tea";
        };

        setLastTransaction(drinkName, receiptItems, totalPrice, userInserted, changeDue, totalCalories);

        // ================================================

        // Update custom milk tea statistics
        this.customMilkTeaSold++;
        this.customMilkTeaRevenue += totalPrice;

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

        printNutritionFacts(teaSlot, milkSlot, sugarLevel, iceServings, addonSlots, totalCalories);

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


    public boolean isRestrictedItem(int slot) {

    return slot == BROWN_SUGAR_SYRUP || slot == CREAM_CHEESE || slot == TAPIOCA_PEARLS || slot == GLASS_JELLY;
        }


    private void printNutritionFacts(int teaSlot, int milkSlot, int sugarLevel, int iceServings, ArrayList<Integer> addonSlots, double totalCalories) {

        System.out.println("\n========== NUTRITION FACTS ==========");

        System.out.println("Calories : " + totalCalories + " kcal");
        System.out.println();

        // =========================
        // CONTAINS
        // =========================
        System.out.println("Contains:");

        if (teaSlot == BLACK_TEA || teaSlot == GREEN_TEA || teaSlot == EARL_GREY_TEA || teaSlot == OOLONG_TEA) {

            System.out.println("[X] Caffeine");
        }

        if (milkSlot == WHOLE_MILK || milkSlot == SKIM_MILK) {

            System.out.println("[X] Dairy");
        }

        System.out.println();

        // =========================
        // SUGAR LEVEL
        // =========================
        System.out.println("Sugar Level:");

        switch (sugarLevel) {

            case NO_SUGAR:
                System.out.println("[X] 0%");
                break;

            case HALF_SUGAR:
                System.out.println("[X] 50%");
                break;

            case FULL_SUGAR:
                System.out.println("[X] 100%");
                break;
        }

        System.out.println();

        // =========================
        // ALLERGENS
        // =========================
        System.out.println("Allergens:");

        boolean allergenFound = false;

        if (milkSlot == WHOLE_MILK || milkSlot == SKIM_MILK) {

            System.out.println("[X] Milk");
            allergenFound = true;
        }

        if (milkSlot == ALMOND_MILK) {
            System.out.println("[X] Tree Nuts");
            allergenFound = true;
        }

        if (addonSlots.contains(OREO)) {
            System.out.println("[X] Gluten");
            allergenFound = true;
        }

        if (!allergenFound) {
            System.out.println("[X] None");
        }

        System.out.println();

        // =========================
        // NUTRITION NOTES
        // =========================
        System.out.println("Nutrition Notes:");

        // Sugar description
        switch (sugarLevel) {

            case NO_SUGAR:
                System.out.println("[X] No Added Sugar");
                break;

            case HALF_SUGAR:
                System.out.println("[X] Moderate Sugar");
                break;

            case FULL_SUGAR:
                System.out.println("[X] High Sugar");
                break;
        }

        // Calorie description
        if (totalCalories < 250) {

            System.out.println("[X] Low Calorie");

        } else if (totalCalories < 500) {

            System.out.println("[X] Moderate Calorie");

        } else {

            System.out.println("[X] High Calorie");
        }

        // Dairy note
        if (milkSlot == OAT_MILK || milkSlot == ALMOND_MILK) {

            System.out.println("[X] Dairy-Free");

        } else {

            System.out.println("[X] Contains Dairy");
        }

        // Temperature
        if (iceServings > 0) {

            System.out.println("[X] Served Cold");
        }

        // Vegetarian
        System.out.println("[X] Vegetarian");

        System.out.println("===================================="); 
    }

    private boolean hasSales() {

        for (int sold : totalSold) {

            if (sold > 0) {
                return true;
            }
        }

        return false;
    }

    public void printMachineInsights() {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                     MACHINE INSIGHTS");
        System.out.println("==============================================================");
        if (!hasSales()) {

            System.out.println("No sales data available yet.");

        } else {

            int bestTea = getBestSellingItem(BLACK_TEA, OOLONG_TEA);

            int bestMilk = getBestSellingItem(WHOLE_MILK, SKIM_MILK);

            int bestSweetener = getBestSellingItem(HONEY, BROWN_SUGAR_SYRUP);

            int bestFlavor = getBestSellingItem(MATCHA_POWDER, TARO_POWDER);

            int bestTopping = getBestSellingItem(OREO, EGG_PUDDING);

            int overall = getOverallBestSeller();

            int highestRevenue = getHighestRevenueItem();

            printBestSellingCategory("Best Selling Tea", bestTea);
            printBestSellingCategory("Best Selling Milk", bestMilk);
            printBestSellingCategory("Best Selling Sweetener", bestSweetener);
            printBestSellingCategory("Best Selling Flavor", bestFlavor);
            printBestSellingCategory("Best Selling Topping", bestTopping);

            System.out.println("--------------------------------------------------------------");
            System.out.printf("Overall Best Seller     : %s (%d sold)%n", itemTemplates[overall].getName(), totalSold[overall]);
            
            System.out.println("--------------------------------------------------------------");

            System.out.printf("Highest Revenue Item    : %s%n", itemTemplates[highestRevenue].getName());

            System.out.printf("Revenue Generated       : PHP %.2f%n", revenuePerItem[highestRevenue]);

            System.out.println("--------------------------------------------------------------");
            System.out.println("Analytics are based on");
            System.out.println("current machine sales records.");
            }

        System.out.println("==============================================================");
    }

    private void printBestSellingCategory(String category, int slot) {
        if (slot == -1) {
            System.out.printf("%-24s: No sales%n", category);
        } else {
            System.out.printf("%-24s: %s (%d sold)%n",
                category,
                itemTemplates[slot].getName(),
                totalSold[slot]);
        }
    }

    @Override
    public void collectMoney() {

        super.collectMoney();

        customMilkTeaSold = 0;
        customMilkTeaRevenue = 0;
    }

    // Getters

    private int getBestSellingItem(int startSlot, int endSlot) {

        int bestSlot = -1;

        for (int i = startSlot; i <= endSlot; i++) {
            if (totalSold[i] > 0) {
                if (bestSlot == -1 || totalSold[i] > totalSold[bestSlot]) {
                    bestSlot = i;
                }
            }
        }

        return bestSlot;
    }

    private int getOverallBestSeller() {

        int bestSlot = 0;

        for (int i = 1; i < totalSold.length; i++) {
            if (totalSold[i] > totalSold[bestSlot]) {
                bestSlot = i;
            }
        }

        return bestSlot;
    }

    private int getHighestRevenueItem() {

        int best = 0;

        for (int i = 1; i < revenuePerItem.length; i++) {

            if (revenuePerItem[i] > revenuePerItem[best]) {
                best = i;
            }
        }

        return best;
    }


    public int getCustomMilkTeaSold() {
        return customMilkTeaSold;
    }

    public double getCustomMilkTeaRevenue() {
        return customMilkTeaRevenue;
    }



}
