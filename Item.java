
public class Item {
    private String name;
    private double price;
    private double calories; 

    /**
     * Creates an item.
     *
     * @param name Name of the item
     * @param price Price of the item
     * @param calories Calories of the item
     */
    public Item(String name, double price, double calories) {
        this.name = name;
        this.price = price;
        this.calories = calories;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getCalories() {
        return calories;
    }

    // Setter
    /**
     * Updates the price of the item.
     *
     * @param price The new price
     * @return true if the price was updated, false otherwise
     */
    public boolean setPrice(double price) {
        if (price >= 0) {
            this.price = price;
            return true;
        }
        return false;
    }

    
    //Displays item information. ** can be improved later on
     
    public void displayItemInfo() {
        System.out.println("src.main.Item: " + name);
        System.out.println("Price: ₱" + price);
        System.out.println("Calories: " + calories + " kcal");
    }
}