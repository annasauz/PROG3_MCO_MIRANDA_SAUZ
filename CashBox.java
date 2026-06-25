import java.util.Arrays;

public class CashBox {
    private int[] denominations = {1,5,10,20,50,100,200,500,1000};
    private int[] denominationsAmount;

    // Constructors
    /**
     * Initializes a new CashBox with zero amounts for each denomination.
     */
    public CashBox(){
        denominationsAmount = new int[denominations.length];
    }

    // Methods
    /**
     * Adds a specified amount of value to the cash box
     *
     * @param denomination face value of currency
     * @param amount amount of denomination passed
     */
    public void addToCashBox(int denomination, int amount) {
        boolean containsValue = Arrays.stream(denominations).anyMatch(x -> x == denomination);

        if (denomination < 0){
            System.out.println("Invalid denomination");
            return;
        }

        if (!containsValue){
            System.out.println("Invalid denomination");
            return;
        }

        if (containsValue){
            for (int i = 0; i < denominations.length; i++){
                if (denominations[i] == denomination){
                    denominationsAmount[i] += amount;
                }
            }
        }
    }

    // Getter
    public double getMoneyAmount() {
        double totalAmount = 0;
        
        for (int i = 0; i < denominations.length; i++){
            totalAmount += (denominations[i] * denominationsAmount[i]);
        }

        return totalAmount;
    }

    public int[] getDenominations() {
        return denominations;
    }

    public int[] getDenominationsAmount() {
        return denominationsAmount;
    }
}
