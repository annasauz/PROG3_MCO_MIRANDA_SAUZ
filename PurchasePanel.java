import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PurchasePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel creditLabel;
    private VendingMachineGUI gui;
    private JButton buildCustomMilkTea;

    public PurchasePanel(VendingMachineGUI gui){
        this.gui = gui;
        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "PURCHASE ITEMS",
                JLabel.CENTER);

        title.setFont(FontStyle.TITLE);

        add(title, BorderLayout.NORTH);

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

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        //----------------------------------

        JPanel bottom = new JPanel(new FlowLayout());
        JButton insertMoney = new JButton("Insert Money");
        JButton buyItem = new JButton("Buy Selected Item");
        buildCustomMilkTea = new JButton("Build Custom Milk Tea");
        JButton returnChange = new JButton("Return Change");
        JButton back = new JButton("Back");

        creditLabel = new JLabel("Credit: PHP 0.00");
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

        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an item from the table first!", 
                "No Item Selected", 
                JOptionPane.WARNING_MESSAGE);
            return false; // Returns boolean!
        }

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        int result = JOptionPane.showConfirmDialog(
                this, 
                spinner, 
                "Select Quantity to Buy", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE
        );


        if (result != JOptionPane.OK_OPTION) {
            return false;
        }


        int quantity = (Integer) spinner.getValue();

        RegularVendingMachine machine;
        
        if (gui.isViewingSpecialMachine()) {
            machine = gui.getSpecialMachine();
        } else {
            machine = gui.getRegularMachine();
        }

        if (machine == null) {
            JOptionPane.showMessageDialog(this, "No active vending machine found!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        
      
        boolean success = machine.purchaseItem(selectedRow);

        if (success) {

            completePurchaseFlow(machine);
            return true;
        }
         else {
            JOptionPane.showMessageDialog(this, 
                "Transaction Failed! Make sure you inserted enough money and the item is in stock.", 
                "Purchase Failed", 
                JOptionPane.ERROR_MESSAGE);
            
            return false; 
        }
   
    }    
    public void loadItems(RegularVendingMachine machine){

        model.setRowCount(0);

        if(machine != null){

            SlotCompartment[] slots = machine.getSlots();

            Item[] items = machine.getItemTemplates();

            for(int i = 0; i < slots.length; i++){

                if(items[i] != null){

                    model.addRow(new Object[]{
                        i + 1,
                        items[i].getName(),
                        "PHP " + String.format("%.2f", items[i].getPrice()),
                        (int) items[i].getCalories(),
                        slots[i].getCurrentInSlotItems()
                    });

                }   

            }

        }

    }

    public void refreshCredit(VendingMachineGUI gui){

        creditLabel.setText(
            "Credit: PHP " + String.format("%.2f", gui.getInsertedMoney())
        );

    }

    private void showCustomerRatingDialog(RegularVendingMachine machine) {

        String[] ratingOptions = {"1 - Poor", "2 - Fair", "3 - Good", "4 - Very Good", "5 - Excellent"};

        String selectedRating = (String) JOptionPane.showInputDialog(this, "How would you rate your purchase?", "Rate Your Experience", JOptionPane.QUESTION_MESSAGE, null, ratingOptions, ratingOptions[4]);

        if (selectedRating != null) {

            int rating = Integer.parseInt(selectedRating.substring(0, 1));

            machine.addCustomerRating(rating);

            String responseMessage;

            switch (rating) {
                case 5:
                    responseMessage = "Thank you! We're glad you enjoyed your drink!";
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

    public void refreshMachineButtons() {

        buildCustomMilkTea.setVisible(
            gui.isViewingSpecialMachine()
        );
    }

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
    public void showFeedbackAndReceipt(RegularVendingMachine machine) {
        showCustomerRatingDialog(machine);
        showReceiptChoiceDialog(machine);
    }
}   
