public class CashBox {
    private int[] denominations = {1,5,10,20,50,100,200,500,1000};
    private int[] denominationsAmount;

    // Constructors
    /**
     * Creates a cash box to store money in the vending machine
     */
    public CashBox(){
         denominationsAmount = new int[denominations.length];
    }

    // Methods

    /**
     * Allows to add money to cash box if valid denomination
     *
     * @param denomination face value of money
     * @param amount how much is being put in
     */
    public void addToCashBox(int denomination, int amount) {
        boolean isFound = false;

        if (denomination < 0){
            System.out.println("Invalid denomination");
        }

        for (int i = 0; i < denominations.length; i++){
            if(denominations[i] == denomination){
                denominationsAmount[i] += amount;
                isFound = true;
                break;
            }
        }

        if (!isFound){
            System.out.println("Invalid denomination");
        }
    }

    // Getters, setters
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
