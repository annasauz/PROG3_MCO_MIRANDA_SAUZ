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

public class PurchasePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JLabel creditLabel;
    private VendingMachineGUI gui;

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

        JButton returnChange = new JButton("Return Change");

        JButton back = new JButton("Back");

        creditLabel = new JLabel("Credit: PHP 0.00");
        creditLabel.setFont(FontStyle.NORMAL);
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(creditLabel);

        bottom.add(insertMoney);

        bottom.add(buyItem);

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

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

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

        
        double currentBackendMoney = machine.transactionCashBox.getMoneyAmount();
        double guiMoney = gui.getInsertedMoney();
        
        if (guiMoney > currentBackendMoney) {
            int diff = (int) (guiMoney - currentBackendMoney);
            machine.receivePayment(diff, 1);
        }

      
        boolean success = machine.purchaseItem(selectedRow, quantity);

        // update GUI 
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Purchase successful! Item dispensed.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);

            // sync GUI credit and backend cash credit
            gui.setInsertedMoney(machine.transactionCashBox.getMoneyAmount());

           
            loadItems(machine);
            refreshCredit(gui);
            
            return true; 
        } else {
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
}

