import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Represents the purchase interface for the active vending machine.
 * Extends JPanel to display the item inventory, current credit,
 * purchase controls, and transaction feedback.
 */
public class PurchasePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel creditLabel;
    private VendingMachineGUI gui;
    private JLabel restrictedLabel;
    private JButton buildCustomMilkTea;

    /**
    * Creates the purchase panel and initializes the item table, credit
    * display, purchase controls, and navigation buttons.
    *
    * @param gui the main vending machine GUI used to access the active
    *            machine and navigate between panels
    */
    public PurchasePanel(VendingMachineGUI gui){
        this.gui = gui;
        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "PURCHASE ITEMS",
                JLabel.CENTER);

        title.setFont(FontStyle.TITLE);

        JLabel instructionLabel = new JLabel(
            "Select one item from the table, then click Buy Selected Item.",
            JLabel.CENTER);

        restrictedLabel = new JLabel(
            "* Restricted ingredients cannot be purchased separately.",
            JLabel.CENTER);

        instructionLabel.setFont(FontStyle.NORMAL);
        restrictedLabel.setFont(FontStyle.NORMAL);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        restrictedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(instructionLabel);
        topPanel.add(Box.createVerticalStrut(4));
        topPanel.add(restrictedLabel);
        topPanel.add(Box.createVerticalStrut(10));

        add(topPanel, BorderLayout.NORTH);

        //----------------------------------

        String[] columns = {
                "Slot",
                "Item",
                "Price",
                "Calories",
                "Stock"
        };

        model = new DefaultTableModel(columns, 0){

            @Override
            public boolean isCellEditable(int row, int column){
            return false;
        }
       
    };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        //----------------------------------

        JPanel bottom = new JPanel(new FlowLayout());
        JButton insertMoney = new JButton("Insert Money");
        JButton buyItem = new JButton("Buy Selected Item");
        buildCustomMilkTea = new JButton("Build Special Milk Tea");
        JButton returnChange = new JButton("Return Change");
        JButton back = new JButton("Back");

        creditLabel = new JLabel("Current Credit: PHP 0.00");
        creditLabel.setFont(FontStyle.NORMAL);
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(creditLabel);
        bottom.add(insertMoney);
        bottom.add(buyItem);
        bottom.add(buildCustomMilkTea);
        bottom.add(returnChange);

        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);

        insertMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.showPanel("Insert Money");
            }
        });

        returnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.showPanel("Return Change");
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gui.isViewingSpecialMachine()) {
                    gui.showPanel("Special");
                } else {
                    gui.showPanel("Regular");
                }
            }
        });

       
        buyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                boolean purchaseSuccessful = processPurchase();
                
         
                if (purchaseSuccessful) {
                    System.out.println("Transaction completed successfully.");
                } else {
                    System.out.println("Transaction was not completed.");
                }
            }
        });

        buildCustomMilkTea.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.isViewingSpecialMachine()) {
                gui.showPanel("Milk Tea Type");
            }
        }
    });
        buildCustomMilkTea.setVisible(false);
    }
   

    /**
     * Helper method to handle purchase logic.
     * 
     * @return true if purchase was completed, false if error/failed
     */
    public boolean processPurchase() {

        int selectedRow = table.getSelectedRow();
        boolean purchaseSuccessful = false;

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item from the table first.",
                "No Item Selected",
                JOptionPane.WARNING_MESSAGE
            );

        } else {

            int slotIndex =
                (int) model.getValueAt(selectedRow, 0) - 1;

            RegularVendingMachine machine;

            if (gui.isViewingSpecialMachine()) {
                machine = gui.getSpecialMachine();
            } else {
                machine = gui.getRegularMachine();
            }

            if (machine == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "No active vending machine found.",
                    "Machine Error",
                    JOptionPane.ERROR_MESSAGE
                );

            } else {

                boolean purchaseAllowed = true;

                Item selectedItem =
                    machine.getItemTemplates()[slotIndex];

                if (selectedItem instanceof SpecialItem) {

                    SpecialItem specialItem =
                        (SpecialItem) selectedItem;

                    if (!specialItem.isSellableIndividually()) {

                        purchaseAllowed = false;

                        JOptionPane.showMessageDialog(
                            this,
                            selectedItem.getName()
                            + " is a restricted ingredient.\n\n"
                            + "This ingredient is reserved for\n"
                            + "special milk tea recipes and\n"
                            + "cannot be purchased separately.",
                            "Restricted Ingredient",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                }

                if (purchaseAllowed) {

                    double creditBeforePurchase = machine.transactionCashBox.getMoneyAmount();
                    purchaseSuccessful = machine.purchaseItem(slotIndex);

                    double currentCredit = machine.transactionCashBox.getMoneyAmount();

                    gui.setInsertedMoney(currentCredit);
                    refreshCredit(gui);

                    if (purchaseSuccessful) {

                        loadItems(machine);

                        double changeDispensed =
                            creditBeforePurchase - selectedItem.getPrice();

                                String purchaseDetails =
                                    "========== VENDING SUCCESS ==========\n\n"
                                    + "Dispensed: " + selectedItem.getName() + "\n"
                                    + "Calories: " + selectedItem.getCalories() + " kcal\n"
                                    + "Total Change Dispensed: PHP "
                                    + String.format("%.2f", changeDispensed);

                                    JOptionPane.showMessageDialog(
                                        this,
                                        purchaseDetails,
                                        "Purchase Complete",
                                        JOptionPane.INFORMATION_MESSAGE
                                    );

                                  showFeedbackAndReceipt(machine);

                    } else {

                        double itemPrice = selectedItem.getPrice();

                        if (creditBeforePurchase < itemPrice) {

                            double missingAmount =
                            itemPrice - creditBeforePurchase;

                            JOptionPane.showMessageDialog(
                                this,
                                "Insufficient funds.\n\n"
                                + "Item Price: PHP "
                                + String.format("%.2f", itemPrice)
                                + "\n"
                                + "Inserted Credit: PHP "
                                + String.format("%.2f", creditBeforePurchase)
                                + "\n"
                                + "Missing Amount: PHP "
                                + String.format("%.2f", missingAmount)
                                + "\n\n"
                                + "Please insert more money.",
                                "Insufficient Funds",
                                JOptionPane.ERROR_MESSAGE
                            );

                        } else {

                            JOptionPane.showMessageDialog(
                                this,
                                "The purchase could not be completed because\n"
                                + "the machine cannot dispense the required exact change.\n\n"
                                + "Your inserted money has been refunded.",
                                "Exact Change Unavailable",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            }
        }

        return purchaseSuccessful;
    }
     
     /**
    * Loads the active vending machine's items and inventory information
    * into the purchase table. Restricted special ingredients are marked
    * with an asterisk.
    *
    * @param machine the vending machine whose items will be displayed
    */
    public void loadItems(RegularVendingMachine machine) {

        model.setRowCount(0);

        boolean specialMachineActive =
        machine instanceof SpecialVendingMachine;

        restrictedLabel.setVisible(specialMachineActive);
        if (machine != null) {

            SlotCompartment[] slots = machine.getSlots();
            Item[] items = machine.getItemTemplates();

            for (int i = 0; i < slots.length; i++) {

                if (items[i] != null) {

                    Item item = items[i];

                    String itemName = item.getName();

                    if (item instanceof SpecialItem) {

                        SpecialItem specialItem = (SpecialItem) item;

                        if (!specialItem.isSellableIndividually()) {
                            itemName += " *";
                        }
                    }

                    model.addRow(new Object[]{
                        i + 1,
                        itemName,
                        "PHP " + String.format("%.2f", item.getPrice()),
                        (int) item.getCalories(),
                        slots[i].getCurrentInSlotItems()
                    });
                }
            }
        }
    }

    /**
    * Updates the credit label to display the user's current inserted money.
    *
    * @param gui the main vending machine GUI containing the current
    *            inserted-money value
    */
    public void refreshCredit(VendingMachineGUI gui){

        creditLabel.setText(
            "Current Credit: PHP " + String.format("%.2f", gui.getInsertedMoney())
        );

    }

    /**
    * Prompts the customer to rate the completed purchase and stores the
    * selected rating in the active vending machine.
    *
    * @param machine the vending machine that records the customer rating
    */
    private void showCustomerRatingDialog(RegularVendingMachine machine) {

        String[] ratingOptions = {"1 - Poor", "2 - Fair", "3 - Good", "4 - Very Good", "5 - Excellent"};

        String selectedRating = (String) JOptionPane.showInputDialog(this, "How would you rate your purchase?", "Rate Your Experience", JOptionPane.QUESTION_MESSAGE, null, ratingOptions, ratingOptions[4]);

        if (selectedRating != null) {

            int rating = Integer.parseInt(selectedRating.substring(0, 1));

            machine.addCustomerRating(rating);

            String responseMessage;

            switch (rating) {
                case 5:
                    responseMessage = "Thank you! We appreciate your feedback.Thank you! We appreciate your feedback.";
                    break;

                case 4:
                    responseMessage = "Thank you for your positive feedback!";
                    break;

                case 3:
                    responseMessage = "Thank you! We appreciate your feedback.";
                break;

                case 2:
                case 1:
                responseMessage = "Thank you for your feedback.\n" + "We'll strive to serve you better next time.";
                break;

                default:
                responseMessage = "Thank you for your feedback.";
            }

            JOptionPane.showMessageDialog(this, responseMessage, "Rating Submitted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
    * Asks the customer whether a receipt should be displayed for the
    * completed transaction.
     *
     * @param machine the vending machine containing the latest
    *                transaction information
    */
    private void showReceiptChoiceDialog(RegularVendingMachine machine) {

        int choice = JOptionPane.showConfirmDialog(this,
            "Would you like to print a receipt?",
            "Receipt",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            showReceiptDialog(machine);
        }
    }

    /**
    * Builds and displays a scrollable receipt for the most recently
    * completed purchase.
    *
    * @param machine the vending machine containing the latest
    *                transaction information
    */
    private void showReceiptDialog(RegularVendingMachine machine) {

        String receipt = buildReceipt(machine);

        JTextArea receiptArea = new JTextArea(receipt);

        receiptArea.setEditable(false);
        receiptArea.setFocusable(false);
        receiptArea.setFont(FontStyle.NORMAL);
    

        receiptArea.setRows(18);
        receiptArea.setColumns(40);
        receiptArea.setCaretPosition(0);

        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);

        JOptionPane.showMessageDialog(this, receiptScrollPane, "Purchase Receipt", JOptionPane.PLAIN_MESSAGE);
    }

    /**
    * Builds a formatted receipt containing the purchased items, total
    * price, amount paid, change, and calories.
    *
    * @param machine the vending machine containing the latest
    *                transaction information
    * @return a formatted string representing the purchase receipt
    */
    private String buildReceipt(RegularVendingMachine machine) {

        StringBuilder receipt = new StringBuilder();

        receipt.append("========================================\n");
        receipt.append("           PURCHASE RECEIPT\n");
        receipt.append("========================================\n\n");

        receipt.append("Items Purchased\n");
        receipt.append("----------------------------------------\n");

        ArrayList<String> printedItems = new ArrayList<>();

        for (String item : machine.getLastPurchaseItems()) {

            if (!printedItems.contains(item)) {

                int count = 0;

                for (String comparedItem : machine.getLastPurchaseItems()) {

                    if (comparedItem.equals(item)) {
                        count++;
                    }
                }

                if (count == 1) {
                    receipt.append("- ").append(item).append("\n");
                } else {
                    receipt.append("- ").append(item).append(" x").append(count).append("\n");
            }

                printedItems.add(item);
            }
        }   

        receipt.append("----------------------------------------\n");

        receipt.append(String.format("%-14s PHP %.2f%n", "Total Price", machine.getLastPurchasePrice()));

        receipt.append(String.format("%-14s PHP %.2f%n", "Amount Paid", machine.getLastAmountPaid()));

        receipt.append(String.format("%-14s PHP %.2f%n", "Change", machine.getLastChange()));

        receipt.append(String.format("%-14s %.0f kcal%n", "Calories", machine.getLastCalories()));

        receipt.append("----------------------------------------\n\n");

        receipt.append("Thank you for your purchase!\n");

        receipt.append("========================================");

        return receipt.toString();
    }

    /**
    * Updates the visibility of the Special Milk Tea button based on
    * whether the user is viewing the Special Vending Machine.
    */
    public void refreshMachineButtons() {

        buildCustomMilkTea.setVisible(
            gui.isViewingSpecialMachine()
        );
    }

    /**
    * Completes the GUI purchase process by updating the displayed credit
     * and inventory, showing the transaction result, requesting customer
     * feedback, and offering a receipt.
    *
     * @param machine the vending machine that completed the purchase
    */
    public void completePurchaseFlow(RegularVendingMachine machine) {

        gui.setInsertedMoney(machine.transactionCashBox.getMoneyAmount());

        loadItems(machine);
        refreshCredit(gui);

        String successMessage =
            "Transaction complete!\n"
            + "Total Price: PHP "
            + String.format("%.2f", machine.getLastPurchasePrice())
            + "\nCalories: "
            + String.format("%.0f", machine.getLastCalories())
            + " kcal"
            + "\nChange Dispensed: PHP "
            + String.format("%.2f", machine.getLastChange());

        JOptionPane.showMessageDialog(this, successMessage, "Vending Success", JOptionPane.INFORMATION_MESSAGE);

        showCustomerRatingDialog(machine);
        showReceiptChoiceDialog(machine);
    }

    /**
    * Displays the customer-rating prompt and receipt choice after a
    * successful purchase.
    *
    * @param machine the vending machine containing the completed
    *                transaction information
    */
    public void showFeedbackAndReceipt(RegularVendingMachine machine) {
        showCustomerRatingDialog(machine);
        showReceiptChoiceDialog(machine);
    }
}   
