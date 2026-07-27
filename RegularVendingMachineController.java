import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RegularVendingMachineController {
    private TextInterface textInterface;
    private RegularVendingMachine vendingMachine;
    private Scanner scanner;

    // Constructor
    /**
     * Creates a controller for a vending machine
     *
     * @param textInterface shared instance of TextInterface
     * @param vendingMachine instance of RegularVendingMachine
     * @param scanner shared scanner
     */
    public RegularVendingMachineController(TextInterface textInterface, RegularVendingMachine vendingMachine, Scanner scanner) {
        this.textInterface = textInterface;
        this.vendingMachine = vendingMachine;
        this.scanner = scanner;
    }

    // Methods
    /**
     * Prints test menu (vending and maintenance tests)
     */
    public void testingMenu() {

    boolean isRunningTest = true;

    while (isRunningTest) {
        textInterface.printTestMenu();

        int choice = getInput(1, 3);

        switch (choice) {

            case 1:
                vendingFeatures();
                break;

            case 2:
                maintenanceFeatures();
                break;

            case 3:
                isRunningTest = false;
                break;

            default:
                System.out.println("Invalid user choice");
                textInterface.pressEnterToContinue(scanner);
        }
    }
}

    /**
     * Prints and allows users to choose regular vending machine features
     */
    private void vendingFeatures() {
        boolean isRunningVending = true;


        while (isRunningVending) {
            textInterface.printVendingFeatures();

            int option = getInput(1, 3);

            switch (option) {
                case 1:
                    displayItemsHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 2:
                    purchaseHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 3:
                    isRunningVending = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Prints and allows users to maintain the vending machine
     */
    private void maintenanceFeatures() {

        boolean isRunningMaintenance = true;

        while (isRunningMaintenance) {

            textInterface.printMaintenanceFeatures();

            int option = getInput(1, 8);

            switch (option) {
                case 1:
                    maintenancePriceHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 2:
                    maintenanceStockHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 3:
                    maintenanceAddChangeHandler();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 4:
                    vendingMachine.collectMoney();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 5:
                    vendingMachine.printTransactionSummary();
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 6:
                    textInterface.printChangeInventory(vendingMachine.getInternalCashBox());
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 7:
                    if (vendingMachine instanceof SpecialVendingMachine) {
                    SpecialVendingMachine svm = (SpecialVendingMachine) vendingMachine;
                    svm.printMachineInsights();
                    } else {
                    System.out.println("Machine Insights are only available for the Special Vending Machine.");
                    }
                    textInterface.pressEnterToContinue(scanner);
                    break;
                case 8:
                    isRunningMaintenance = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
            }
        }
    }


    /**
     * Displays the items available in the vending machine.
     */
    protected void displayItemsHandler() {

        System.out.println();

        if (vendingMachine instanceof SpecialVendingMachine) {

            textInterface.printSpecialVendingMachineMenu(vendingMachine.getItemTemplates(), vendingMachine.getSlots());

        } else {

        textInterface.printRegularVendingMachineMenu(vendingMachine.getItemTemplates(), vendingMachine.getSlots());
        }

        System.out.println();
    }
    /**
     * Handles the addition of money to the vending machine.
     */
    private void addMoneyHandler() {
        System.out.println("\n--- Add Money ---");
        displayItemsHandler();
        System.out.println("Denomination (PHP)");
        System.out.println("1, 5, 10, 20, 50, 100, 200, 500, 1000");
        System.out.print("Choice: ");
        int denomination = getMoneyDenomination();

        System.out.print("Enter quantity: ");
        int quantity = getPositiveInteger();

        vendingMachine.receivePayment(denomination, quantity);
        System.out.println();
    }


    /**
     * Handles the purchase of an item from the vending machine.
     */
    private void purchaseHandler() {
        boolean isPurchasing = true;
        int maxSlots = vendingMachine.getSlots().length;
        while (isPurchasing) {

        int choice;

        if (vendingMachine instanceof SpecialVendingMachine) {
        textInterface.purchaseMenuSpecial();
        choice = getInput(1, 4);
        } else {
        textInterface.purchaseMenuRegular();
        choice = getInput(1, 3);
        }

            switch (choice) {
                case 1:
                    addMoneyHandler();
                    break;
                case 2:
                        displayItemsHandler();

                        System.out.print("\nSelect an item to purchase (1-" + maxSlots + "): ");
                        int slotChoice = getInput(1, maxSlots) - 1;

                        boolean restricted = false;

                        if (vendingMachine instanceof SpecialVendingMachine) {

                            SpecialVendingMachine specialMachine = (SpecialVendingMachine) vendingMachine;

                            if (specialMachine.isRestrictedItem(slotChoice)) {

                                restricted = true;

                                System.out.println("\n========== PURCHASE NOT ALLOWED ==========");
                                System.out.println("The selected item is marked as (Restricted)");
                                System.out.println("and cannot be purchased individually.");
                                System.out.println("It is only available as an ingredient");
                                System.out.println("for a custom milk tea.");
                                System.out.println("==========================================");

                                System.out.println();
                                System.out.println("Please select another item.");
                            }
                        }

                        if (!restricted) {

                            System.out.print("\nAmount buying: ");
                            int quantity = getInput(1, 10);

                            boolean success = vendingMachine.purchaseItem(slotChoice, quantity);

                            if (success) {
                                customerRatingHandler();
                                receiptHandler();
                                isPurchasing = false;
                        }
                        else {    
                            textInterface.pressEnterToContinue(scanner);
                        }
                    }               
                break;
                case 3:
                if (vendingMachine instanceof SpecialVendingMachine) {
                    if (customMilkTeaHandler()) {
                        customerRatingHandler();
                        receiptHandler();
                        isPurchasing = false;
                    }
                    else {      
                        textInterface.pressEnterToContinue(scanner);
                    }
                    } else {
                    vendingMachine.produceChangeWithoutPurchase();
                    isPurchasing = false;
                    }
                break;
                case 4:
                    vendingMachine.produceChangeWithoutPurchase();
                    isPurchasing = false;
                    break;
                default:
                    System.out.println("Invalid user choice");
                    textInterface.pressEnterToContinue(scanner);
                    break;    
            }
        }
    }



    /**
     * Handles maintenance of stock of an item.
     */
    private void maintenanceStockHandler() {
        System.out.println("\n--- Restock Items ---");
        textInterface.printMaintenanceStockDisplay(vendingMachine.getItemTemplates(), vendingMachine.getSlots());
        int maxSlots = vendingMachine.getSlots().length;
        System.out.print("Select a slot to restock (1-" + maxSlots + "): ");
        int userInput = getInput(1, maxSlots);
       if (userInput <= 0) {
            System.out.println("Invalid slot choice. Returning to menu.");
        } else {
            int slotChoice = userInput - 1; 

            Item existingItem = vendingMachine.getItemTemplates()[slotChoice];

            if (existingItem != null) {

                System.out.println("\nCurrent item: " + existingItem.getName());

                System.out.print("Enter quantity to add: ");
                int quantity = getPositiveInteger();

                vendingMachine.restockSlot(slotChoice, existingItem, quantity);

            } else {
                System.out.println("Unable to restock. The selected slot has not been initialized.");
            }
        }
    }

    /**
     * Allows user to update price/s of items
     */
    private void maintenancePriceHandler() {
        displayItemsHandler();
        int maxSlots = vendingMachine.getSlots().length;
        System.out.print("Select an item to update (1-" + maxSlots + "): ");
        int userInput = getInput(1, maxSlots);
        if (userInput <= 0) {
            System.out.println("Invalid slot choice. Returning to menu.");
        } else {           
            int slotChoice = userInput - 1; 
            Item item = vendingMachine.getItemTemplates()[slotChoice];

            if (item == null) {
                System.out.println("Item does not exist in this slot.");
            } else {
                System.out.print("Input new price: ");
                double itemPrice = getPositiveDouble();                
                if (item.setPrice(itemPrice)) {
                    System.out.println("Price updated successfully.");
                } else {
                    System.out.println("Invalid price.");
                }
            }
        }
    }

    /**
     * Allows user to replenish change inside the vending machine
     */
    private void maintenanceAddChangeHandler(){
        System.out.println("\n--- Replenish Change ---");
        System.out.println("Accepted Denominations (PHP)");
        System.out.println("1, 5, 10, 20, 50, 100, 200, 500, 1000");
        System.out.print("Choice: ");
        int denomination = getMoneyDenomination();
        System.out.print("Enter quantity: ");
        int quantity = getPositiveInteger();
        vendingMachine.replenishChangeReserves(denomination,quantity);
    }

    /**
     * Gets user input and validates it.
     *
     * @param min smallest user choice
     * @param max largest user choice
     * @return the user choice if within range, otherwise returns 0
     */
    private int getInput(int min, int max) {
        boolean valid = false;
        int input = 0;
        String leftover;

        while (!valid) {
            System.out.print("Menu Choice: ");

            // check if num
            if (scanner.hasNextInt()) {
                input = scanner.nextInt(); 
                leftover = scanner.nextLine().trim();

                if (!leftover.isEmpty()) {
                    System.out.println("Invalid input. Please enter only one whole number.");
                }
                
                else if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between "
                            + min + " and " + max + ".");
                }
            } else {
                // for letter and symbols
                System.out.println("Invalid input. Please enter a whole number.");
                
                // clear to avoid looping
                scanner.nextLine(); 
            }
        }

        return input;
    }

    /**
     * Checks if valid positive integer.
     *
     * @return checked integer
     */

    private int getPositiveInteger() {
    boolean valid = false;
    int value = 0;
    String leftover;

    while (!valid) {
        if (scanner.hasNextInt()) {
            value = scanner.nextInt();
            leftover = scanner.nextLine().trim();

            if (!leftover.isEmpty()) {
                System.out.println("Invalid input. Please enter only one whole number.");
            } else if (value > 0) {
                valid = true;
            } else {
                System.out.println("Value must be greater than zero. Try again:");
            }
        } else {
            System.out.println("Invalid input. Enter a whole number.");
            scanner.nextLine(); // clear buffer
        }
    }
    return value;
}

    /**
     * Checks if valid positive double.
     *
     * @return checked double
     */
    private double getPositiveDouble() {
        boolean valid = false;
        double value = 0;
        String leftover;

        while (!valid) {
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                leftover = scanner.nextLine().trim();

                if (!leftover.isEmpty()) {
                    System.out.println("Invalid input. Please enter only one number.");
                } else if (value > 0) {
                    valid = true;
                } else {
                    System.out.println("Value must be greater than zero. Try again:");
                }
            } else {
                System.out.println("Invalid input. Enter a valid number/decimal.");
                scanner.nextLine(); // clear buffer
            }
        }
        return value;
    }
    /**
     * Checks if valid denomination.
     *
     * @return checked denomination
     */

    private int getMoneyDenomination() {
        boolean valid = false;
        int denomination = 0;
        String leftover;

        while (!valid) {
            if (scanner.hasNextInt()) {
                denomination = scanner.nextInt();
                leftover = scanner.nextLine().trim();

                if (!leftover.isEmpty()) {
                    System.out.println("Invalid input. Please enter only one whole number.");
                    System.out.println("Denomination (PHP)");
                    System.out.println("1, 5, 10, 20, 50, 100, 200, 500, 1000");
                    System.out.print("Choice: ");
                } else {
                    switch (denomination) {
                        case 1:
                        case 5: 
                        case 10: 
                        case 20: 
                        case 50: 
                        case 100: 
                        case 200: 
                        case 500: 
                        case 1000:
                            valid = true;
                            break;
                        default:
                            System.out.println("Invalid denomination.");
                            System.out.println("Denomination (PHP)");
                            System.out.println("1, 5, 10, 20, 50, 100, 200, 500, 1000");
                            System.out.print("Choice: ");
                            break;
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter a valid denomination.");
                scanner.nextLine(); // clear buffer
                System.out.println("Denomination (PHP)");
                System.out.println("1, 5, 10, 20, 50, 100, 200, 500, 1000");
                System.out.print("Choice: ");
            }
        }
        return denomination;
    }
 

    /* =============================================
            SPECIAL VENDING MACHINE METHODS
   =============================================== */

private int chooseTea() {

        System.out.println("\n===== CHOOSE TEA BASE =====");
        System.out.println("1. Black Tea");
        System.out.println("2. Green Tea");
        System.out.println("3. Earl Grey Tea");
        System.out.println("4. Oolong Tea");

        System.out.print("Choice: ");
        int choice = getInput(1, 4);

        switch (choice) {
            case 1:
                return SpecialVendingMachine.BLACK_TEA;

            case 2:
                return SpecialVendingMachine.GREEN_TEA;

            case 3:
                return SpecialVendingMachine.EARL_GREY_TEA;

            default:
                return SpecialVendingMachine.OOLONG_TEA;
        }
    }

    private int chooseMilk() {

        System.out.println("\n===== CHOOSE MILK =====");
        System.out.println("1. Whole Milk");
        System.out.println("2. Oat Milk");
        System.out.println("3. Almond Milk");
        System.out.println("4. Skim Milk");

        System.out.print("Choice: ");
        int choice = getInput(1, 4);

        switch (choice) {
            case 1:
                return SpecialVendingMachine.WHOLE_MILK;

            case 2:
                return SpecialVendingMachine.OAT_MILK;

            case 3:
                return SpecialVendingMachine.ALMOND_MILK;

            default:
                return SpecialVendingMachine.SKIM_MILK;
        }
    }

    private int chooseMilk(int randomChoice) {
        switch (randomChoice) {
            case 1:
                return SpecialVendingMachine.WHOLE_MILK;

            case 2:
                return SpecialVendingMachine.OAT_MILK;

            case 3:
                return SpecialVendingMachine.ALMOND_MILK;

            default:
                return SpecialVendingMachine.SKIM_MILK;
        }

    }

private ArrayList<Integer> chooseAddons() {

    SpecialVendingMachine specialMachine = (SpecialVendingMachine) vendingMachine;

    ArrayList<Integer> addons = new ArrayList<>();

    while (true) {

        System.out.println("\n========== ADD-ONS ==========");

        System.out.println("Current Selection:");

        if (addons.isEmpty()) {
            System.out.println("None");
        } else {

            for (Integer slot : addons) {
                System.out.println("- " +
                    specialMachine.getItemTemplates()[slot].getName());
            }
        }

        System.out.println();

        System.out.println("1. Matcha Powder");
        System.out.println("2. Taro Powder");
        System.out.println("3. Oreo");
        System.out.println("4. Cream Cheese");
        System.out.println("5. Tapioca Pearls");
        System.out.println("6. Glass Jelly");
        System.out.println("7. Egg Pudding");
        System.out.println("0. Finish");

        System.out.print("Choice: ");
        int choice = getInput(0, 7);

        int slot = -1;

        switch(choice){

            case 0: return addons;
            case 1: slot = SpecialVendingMachine.MATCHA_POWDER; break;
            case 2: slot = SpecialVendingMachine.TARO_POWDER; break;
            case 3: slot = SpecialVendingMachine.OREO; break;
            case 4: slot = SpecialVendingMachine.CREAM_CHEESE; break;
            case 5: slot = SpecialVendingMachine.TAPIOCA_PEARLS; break;
            case 6: slot = SpecialVendingMachine.GLASS_JELLY; break;
            case 7: slot = SpecialVendingMachine.EGG_PUDDING; break;
        }

        if(addons.contains(slot)){
            System.out.println("That add-on has already been selected.");
        }
        else{
            addons.add(slot);
            System.out.println("Added " + specialMachine.getItemTemplates()[slot].getName());
            }
        }   
        
    }

    private void chooseAddons(int addon, ArrayList<Integer> addons) {
        SpecialVendingMachine specialMachine = (SpecialVendingMachine) vendingMachine;

        int slot = -1;

        switch(addon){
            case 0: return;
            case 1: slot = SpecialVendingMachine.MATCHA_POWDER; break;
            case 2: slot = SpecialVendingMachine.TARO_POWDER; break;
            case 3: slot = SpecialVendingMachine.OREO; break;
            case 4: slot = SpecialVendingMachine.CREAM_CHEESE; break;
            case 5: slot = SpecialVendingMachine.TAPIOCA_PEARLS; break;
            case 6: slot = SpecialVendingMachine.GLASS_JELLY; break;
            case 7: slot = SpecialVendingMachine.EGG_PUDDING; break;
        }

        addons.add(slot);
        System.out.println("Added " + specialMachine.getItemTemplates()[addon].getName());
    }

    private int chooseSize() {

        System.out.println("\n===== CHOOSE SIZE =====");
        System.out.println("1. Small");
        System.out.println("2. Medium");
        System.out.println("3. Large");

        System.out.print("Choice: ");
        int choice = getInput(1, 3);

        switch (choice) {
            case 1:
                return SpecialVendingMachine.SMALL;

            case 2:
                return SpecialVendingMachine.MEDIUM;

            default:
                return SpecialVendingMachine.LARGE;
        }
    }
    
    private boolean customMilkTeaHandler() {
        SpecialVendingMachine specialMachine = (SpecialVendingMachine) vendingMachine;
        int tea;
        int milk;
        int sweetener;
        int sugarLevel;
        int iceLevel;
        int size;
        ArrayList<Integer> addons;

        textInterface.printMilkTeaToBuy();
        switch (getInput(1, 4)) {
            case 1:
                break;
            case 2:
                textInterface.printSignatureDrinks();
                switch (getInput(1, 4)) {
                    case 1:
                        addons = new ArrayList<>();
                        addons.add(SpecialVendingMachine.MATCHA_POWDER);
                        addons.add(SpecialVendingMachine.EGG_PUDDING);
                        textInterface.printCustomMilkTeaSummary(
                                specialMachine,
                                SpecialVendingMachine.BLACK_TEA,
                                SpecialVendingMachine.WHOLE_MILK,
                                SpecialVendingMachine.BROWN_SUGAR_SYRUP,
                                SpecialVendingMachine.FULL_SUGAR,
                                SpecialVendingMachine.REGULAR_ICE,
                                SpecialVendingMachine.LARGE,
                                addons);
                        return specialMachine.purchaseCustomMilkTea(SpecialVendingMachine.OOLONG_TEA,
                                                                    SpecialVendingMachine.OAT_MILK,
                                                                    SpecialVendingMachine.HONEY,
                                                                    SpecialVendingMachine.HALF_SUGAR,
                                                                    addons,
                                                                    SpecialVendingMachine.LESS_ICE,
                                                                    SpecialVendingMachine.MEDIUM);
                    case 2:
                        addons = new ArrayList<>();
                        addons.add(SpecialVendingMachine.TAPIOCA_PEARLS);
                        addons.add(SpecialVendingMachine.CREAM_CHEESE);
                        textInterface.printCustomMilkTeaSummary(
                                specialMachine,
                                SpecialVendingMachine.EARL_GREY_TEA,
                                SpecialVendingMachine.WHOLE_MILK,
                                SpecialVendingMachine.BROWN_SUGAR_SYRUP,
                                SpecialVendingMachine.HALF_SUGAR,
                                SpecialVendingMachine.REGULAR_ICE,
                                SpecialVendingMachine.LARGE,
                                addons);
                        return specialMachine.purchaseCustomMilkTea(SpecialVendingMachine.EARL_GREY_TEA,
                                                                    SpecialVendingMachine.WHOLE_MILK,
                                                                    SpecialVendingMachine.BROWN_SUGAR_SYRUP,
                                                                    SpecialVendingMachine.HALF_SUGAR,
                                                                    addons,
                                                                    SpecialVendingMachine.REGULAR_ICE,
                                                                    SpecialVendingMachine.LARGE);
                    case 3:
                        addons = new ArrayList<>();
                        addons.add(SpecialVendingMachine.TARO_POWDER);
                        addons.add(SpecialVendingMachine.OREO);
                        addons.add(SpecialVendingMachine.GLASS_JELLY);
                        textInterface.printCustomMilkTeaSummary(
                                specialMachine,
                                SpecialVendingMachine.GREEN_TEA,
                                SpecialVendingMachine.WHOLE_MILK,
                                SpecialVendingMachine.HONEY,
                                SpecialVendingMachine.NO_SUGAR,
                                SpecialVendingMachine.REGULAR_ICE,
                                SpecialVendingMachine.SMALL,
                                addons);
                        return specialMachine.purchaseCustomMilkTea(SpecialVendingMachine.GREEN_TEA,
                                                                    SpecialVendingMachine.WHOLE_MILK,
                                                                    SpecialVendingMachine.HONEY,
                                                                    SpecialVendingMachine.NO_SUGAR,
                                                                    addons,
                                                                    SpecialVendingMachine.REGULAR_ICE,
                                                                    SpecialVendingMachine.SMALL);
                    case 4:
                        return false;
                }
                return false;
            case 3:
                Random random = new Random();
                tea = random.nextInt(4);
                milk = chooseMilk(random.nextInt(4));
                sweetener = chooseSweetener(random.nextInt(3));
                sugarLevel = random.nextInt(3);
                iceLevel = random.nextInt(4);
                size = random.nextInt(3) + 1;

                addons = new ArrayList<>();
                int addonCount = random.nextInt(4);
                for (int i = 0; i < addonCount; i++) {
                    int addon = random.nextInt(7);
                    chooseAddons(addon, addons);
                }

                textInterface.printCustomMilkTeaSummary(
                        specialMachine,
                        tea,
                        milk,
                        sweetener,
                        sugarLevel,
                        iceLevel,
                        size,
                        addons
                );
                return specialMachine.purchaseCustomMilkTea(tea, milk, sweetener, sugarLevel, addons, iceLevel, size);
            case 4:
                return false;
            default:
                System.out.println("Invalid user choice");
                textInterface.pressEnterToContinue(scanner);
                return false;
        }

        tea = chooseTea();
        milk = chooseMilk();

        sweetener = chooseSweetener();

        sugarLevel = SpecialVendingMachine.FULL_SUGAR;

        if (sweetener != -1) {
            sugarLevel = chooseSugarLevel();
            }

        addons = chooseAddons();

        iceLevel = chooseIceLevel();

        size = chooseSize();

        textInterface.printCustomMilkTeaSummary(specialMachine, tea, milk, sweetener, sugarLevel, iceLevel, size, addons);


    return specialMachine.purchaseCustomMilkTea(tea, milk, sweetener, sugarLevel, addons, iceLevel, size);   

}

private int chooseSugarLevel() {

    System.out.println("\n===== CHOOSE SUGAR LEVEL =====");
    System.out.println("1. 0%");
    System.out.println("2. 50%");
    System.out.println("3. 100%");

    System.out.print("Choice: ");

    int choice = getInput(1, 3);

    switch (choice) {

        case 1:
            return SpecialVendingMachine.NO_SUGAR;

        case 2:
            return SpecialVendingMachine.HALF_SUGAR;

        default:
            return SpecialVendingMachine.FULL_SUGAR;
    }
}

    private int chooseSweetener() {

        System.out.println("\n===== CHOOSE SWEETENER =====");
        System.out.println("1. None");
        System.out.println("2. Honey");
        System.out.println("3. Brown Sugar Syrup");

        System.out.print("Choice: ");

        int choice = getInput(1, 3);

        switch (choice) {

            case 1:
                return -1;
            case 2:
                return SpecialVendingMachine.HONEY;

            default:
                return SpecialVendingMachine.BROWN_SUGAR_SYRUP;
        }
    }

    private int chooseSweetener(int randomChoice) {

        switch (randomChoice) {
            case 1:
                return -1;
            case 2:
                return SpecialVendingMachine.HONEY;

            default:
                return SpecialVendingMachine.BROWN_SUGAR_SYRUP;
        }

    }

    private int chooseIceLevel() {

        System.out.println("\n===== CHOOSE ICE LEVEL =====");
        System.out.println("1. No Ice");
        System.out.println("2. Less Ice");
        System.out.println("3. Regular Ice");
        System.out.println("4. Extra Ice");

        System.out.print("Choice: ");
        int choice = getInput(1, 4);

        switch (choice) {

            case 1:
                return SpecialVendingMachine.NO_ICE;

            case 2:
                return SpecialVendingMachine.LESS_ICE;

            case 3:
                return SpecialVendingMachine.REGULAR_ICE;

            default:
                return SpecialVendingMachine.EXTRA_ICE;
        }
    }

    private void customerRatingHandler() {

        System.out.println();

        System.out.println("========== RATE YOUR EXPERIENCE ==========");

        System.out.println("How would you rate your purchase?");

        System.out.println("1 - Poor");
        System.out.println("2 - Fair");
        System.out.println("3 - Good");
        System.out.println("4 - Very Good");
        System.out.println("5 - Excellent");

        System.out.print("Rating (1-5): ");

        int rating = getInput(1, 5);

        vendingMachine.addCustomerRating(rating);

        switch (rating) {

            case 5:
                System.out.println("Thank you! We're glad you enjoyed your drink!");
                break;

            case 4:
                System.out.println("Thank you for your positive feedback!");
                break;

            case 3:
                System.out.println("Thank you! We appreciate your feedback.");
                break;

            case 2:
            case 1:
                System.out.println("Thank you for your feedback.");
                System.out.println("We'll strive to serve you better next time.");
                break;
        }

        System.out.println("==========================================");
    }

    private void receiptHandler() {

        System.out.println();
        System.out.println("Would you like to print a receipt?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Choice: ");

        int choice = getInput(1, 2);

        if (choice == 1) {
            printReceipt();
        }
    }

    private void printReceipt() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           PURCHASE RECEIPT");
        System.out.println("========================================");

        System.out.println();
        System.out.println("Items Purchased");
        System.out.println("----------------------------------------");

        ArrayList<String> printedItems = new ArrayList<>();

        for (String item : vendingMachine.getLastPurchaseItems()) {

            if (!printedItems.contains(item)) {

                int count = 0;

                for (String compare : vendingMachine.getLastPurchaseItems()) {
                    if (compare.equals(item)) {
                        count++;
                    }
                }

                if (count == 1) {
                    System.out.println("- " + item);
                } else {
                    System.out.println("- " + item + " x" + count);
                }

                printedItems.add(item);
            }
        }

        System.out.println("----------------------------------------");

        System.out.printf("%-13s PHP %.2f%n", "Total Price", vendingMachine.getLastPurchasePrice());

        System.out.printf("%-13s PHP %.2f%n", "Amount Paid", vendingMachine.getLastAmountPaid());

        System.out.printf("%-13s PHP %.2f%n", "Change", vendingMachine.getLastChange());

        System.out.printf("%-13s %.0f kcal%n", "Calories", vendingMachine.getLastCalories());

        System.out.println("----------------------------------------");

        System.out.println();
        System.out.println("Thank you for your purchase!");
        System.out.println("========================================");
    }
}
























