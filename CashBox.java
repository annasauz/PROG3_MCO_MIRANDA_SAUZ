import java.util.Arrays;

/**
 * Represents the CashBox of the vending machine.
 * Manages the valid money denominations and tracks the quantity of each currently in the machine.
 */
public class CashBox {
    // valid Philippine Peso denominations
    private final int[] denominations = new int[]{1, 5, 10, 20, 50, 100, 200, 500, 1000};
    
    // tracks the physical quantity/count of each denomination in the machine
    private final int[] denominationsAmount;

    /**
     * Constructor for the CashBox.
     *
     * Initializes the amounts array to properly match the size of the available denominations.
     */
    public CashBox() {
        this.denominationsAmount = new int[this.denominations.length];
    }

    /**
     * Adds a specific quantity of a certain denomination to the cash box reserves.
     *
     * @param denomination The face value of the bill or coin (e.g., 50, 100, 500)
     * @param quantity The number of bills or coins to add
     */
    public void addToCashBox(int denomination, int quantity) {
        // Checks if the provided denomination exists in our valid list using a Stream
        boolean isValidDenomination = Arrays.stream(this.denominations).anyMatch(d -> d == denomination);

        if (denomination < 0) {
            System.out.println("Invalid denomination");
        } else if (!isValidDenomination) {
            System.out.println("Invalid denomination");
        } else {
            // Loop through the denominations array to find the matching index
            for (int i = 0; i < this.denominations.length; i++) {
                if (this.denominations[i] == denomination) {
                    // Add the quantity to the corresponding slot in the amounts array
                    this.denominationsAmount[i] += quantity;
                }
            }
        }
    }

    /**
     * Calculates the total monetary value currently stored inside the cash box.
     *
     * @return The total amount of money
     */
    public double getMoneyAmount() {
        double totalAmount = 0.0;

        // Multiply each denomination by its current quantity and add it to the total
        for (int i = 0; i < this.denominations.length; i++) {
            totalAmount += (double) (this.denominations[i] * this.denominationsAmount[i]);
        }

        return totalAmount;
    }

    /**
     * Retrieves the array containing the valid monetary denominations.
     *
     * @return Array of valid denominations
     */
    public int[] getDenominations() {
        return this.denominations;
    }

    /**
     * Retrieves the array containing the quantity of each denomination currently in the machine.
     *
     * @return Array of denomination quantities
     */
    public int[] getDenominationsAmount() {
        return this.denominationsAmount;
    }
}
