import java.sql.Array;
import java.util.Arrays;

public class CashBox {
    private int[] denominations = {1,5,10,20,50,100,200,500,1000};
    private int[] denominationsAmount;

    // Constructors
    public CashBox(){
         denominationsAmount = new int[denominations.length];
    }

    // Methods
    public void addToCashBox(int denomination, int amount) {
        boolean containsValue = Arrays.asList(denominations).contains(amount);

        if (denomination < 0 || !(containsValue)){
            System.out.println("Invalid denomination");
        }

        if (containsValue){
            //TODO add to the denominationAmount array the param amount taken in.
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
}
