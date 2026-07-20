public class SpecialItem extends Item {
    
    // Flag if can be standalone item or not 
    private boolean isSellableIndividually;

    /**
     * Constructs a SpecialItem inherited from item with a restriction flag.
     * 
     * @param name                   item name
     * @param price                  item cost
     * @param calories              item calories
     * @param isSellableIndividually false if restricted, true if can be independently sold
     */
    public SpecialItem(String name, double price, double calories, boolean isSellableIndividually) {
        super(name, price, calories);
        this.isSellableIndividually = isSellableIndividually;
    }

    /**
     * Constructor that copies
     * New physical instance with SpecialItem's properties.
     * 
     * @param sourceItem The original SpecialItem to copy.
     */
    public SpecialItem(SpecialItem sourceItem) {
        super(sourceItem);
        this.isSellableIndividually = sourceItem.isSellableIndividually();
    }

    /**
     * Checker if item can be sold independently.
     * 
     * @return False if cannot be sold alone, true otherwide.
     */
    public boolean isSellableIndividually() {
        return this.isSellableIndividually;
    }
} 
    

