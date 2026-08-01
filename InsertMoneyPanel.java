import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;   

public class InsertMoneyPanel extends JPanel {
    
    private JLabel amountLabel;
    private VendingMachineGUI gui;

    public InsertMoneyPanel(VendingMachineGUI gui) {
        this.gui = gui;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        
        JLabel title = new JLabel("INSERT MONEY", JLabel.CENTER);
        title.setFont(FontStyle.TITLE);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        
        JPanel display = new JPanel();
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        display.setMaximumSize(new Dimension(400, 50));
        display.setPreferredSize(new Dimension(400, 50));
        display.setAlignmentX(Component.CENTER_ALIGNMENT);

        amountLabel = new JLabel("Credit: PHP 0.00");
        amountLabel.setFont(FontStyle.TITLE);
        display.add(amountLabel);

        centerPanel.add(display);
        centerPanel.add(Box.createVerticalStrut(30));

        // denomination buttons
        JPanel buttonGrid = new JPanel(new GridLayout(3, 3, 15, 15));
        buttonGrid.setMaximumSize(new Dimension(400, 200));
        buttonGrid.setAlignmentX(Component.CENTER_ALIGNMENT);

        int[] denominations = {1, 5, 10, 20, 50, 100, 200, 500, 1000};
        
        for (int denom : denominations) {
            JButton btn = new JButton("PHP " + denom);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            
            
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Ask user input on denomination quantity
                    String input = JOptionPane.showInputDialog(
                            InsertMoneyPanel.this, 
                            "Enter the number of PHP " + denom + " to insert:", 
                            "Enter Quantity", 
                            JOptionPane.QUESTION_MESSAGE
                    );
                    
                    // Input validation
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(input.trim());
                            
                            if (quantity > 0) {

                                boolean insertSuccessful = insertDenomination(denom, quantity);
                                
                                if (insertSuccessful) {
                                    System.out.println("Successfully inserted " + quantity + " pcs of PHP " + denom);
                                }
                            } else {
                                JOptionPane.showMessageDialog(InsertMoneyPanel.this, 
                                        "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(InsertMoneyPanel.this, 
                                    "Please enter a valid whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            
            buttonGrid.add(btn);
        }

        centerPanel.add(buttonGrid);
        add(centerPanel, BorderLayout.CENTER);

        // bottom navigation
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JButton back = new JButton("Done / Back to Purchase");

        back.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.showPanel("Purchase");
        }
    });

    bottom.add(Box.createVerticalStrut(30));
    bottom.add(back);
    bottom.add(Box.createVerticalStrut(20));

    add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Helper method to insert a specific bill/coin to the active machine.
     * 
     * @param amount denomination to insert
     * @return true if successfully inserted, false false otherwise
     */
    private boolean insertDenomination(int amount, int quantity) {
        RegularVendingMachine machine;
        
        if (gui.isViewingSpecialMachine()) {
            machine = gui.getSpecialMachine();
        } else {
            machine = gui.getRegularMachine();
        }

        if (machine == null) {
            JOptionPane.showMessageDialog(this, "No active machine found!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Send to backend cashbox
        machine.receivePayment(amount, quantity);

        // fetch updated total from backend
        double newTotal = machine.transactionCashBox.getMoneyAmount();
        
        // update in gui
        gui.setInsertedMoney(newTotal);

        
        amountLabel.setText("Credit: PHP " + String.format("%.2f", newTotal));
        
        return true; 
    }
    

    /**
     * Label is updated in order to match current balance
     */
    public void refreshDisplay() {
        amountLabel.setText("Credit: PHP " + String.format("%.2f", gui.getInsertedMoney()));
    }
}
