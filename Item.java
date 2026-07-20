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

    /**
     * Method overloading
     * Creates item based on an existing Item.
     * 
     * @param sourceItem The original Item blueprint to copy.
     */
    public Item(Item sourceItem) {
        this.name = sourceItem.getName();
        this.price = sourceItem.getPrice();
        this.calories = sourceItem.getCalories();
    }

    // Getters
    /**
     * Returns the name of the item.
     *
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item.
     *
     * @return The price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the calories of the item.
     *
     * @return The calories of the item
     */
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
    
    /**
     * Displays the item information.
     */
    public void displayItemInfo() {
    System.out.println("Item: " + this.name);
    System.out.println("Price: PHP" + this.price);
    System.out.println("Calories: " + this.calories + " kcal");
}
}

