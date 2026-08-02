import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Represents the return-change interface of the vending machine.
 * Extends JPanel to display the current credit and allow the customer
 * to cancel the transaction and retrieve inserted money.
 */
public class ReturnChangePanel extends JPanel {

    private JLabel displayLabel;
    private VendingMachineGUI gui;

    /**
    * Creates the return-change panel and initializes the current credit
    * display, change dispensing controls, and navigation buttons.
    *
    * @param gui the main vending machine GUI used to access the active
    *            machine, update the displayed credit, and navigate
    *            between panels
    */
    public ReturnChangePanel(VendingMachineGUI gui) {
        this.gui = gui;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("RETURN CHANGE");
        title.setFont(FontStyle.TITLE);
        title.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(60));
        add(title);
        add(Box.createVerticalStrut(40));

       
        displayLabel = new JLabel("Current Credit:" + String.format("PHP %.2f", gui.getInsertedMoney()));
        displayLabel.setFont(FontStyle.NORMAL);
        displayLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(displayLabel);
        add(Box.createVerticalStrut(40));

        JButton returnChange = new JButton("Dispense Change");
        JButton back = new JButton("Back");

        returnChange.setAlignmentX(CENTER_ALIGNMENT);
        back.setAlignmentX(CENTER_ALIGNMENT);

        
        
        
        returnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                boolean success = processReturnChange();
                
                if (success) {
                    System.out.println("Change dispensed successfully.");
                } else {
                    System.out.println("Failed to dispense change (balance was likely 0).");
                }
            }
        });

        
        back.addActionListener(e -> gui.showPanel("Purchase"));

        add(returnChange);
        add(Box.createVerticalStrut(20));
        add(back);
    }

    /**
    * Returns the customer's remaining credit by requesting the active
    * vending machine to dispense the available change and updates the
    * displayed balance.
    *
    * @return true if the change is successfully returned; false otherwise
    */
    private boolean processReturnChange() {
        double currentCredit = gui.getInsertedMoney();

        
        if (currentCredit <= 0) {
            JOptionPane.showMessageDialog(this, "No credit to return.", "Notice", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

      
        RegularVendingMachine machine;
        
        if (gui.isViewingSpecialMachine()) {
            machine = gui.getSpecialMachine();
        } else {
            machine = gui.getRegularMachine();
        }

        if (machine == null) {
            JOptionPane.showMessageDialog(this, "No active machine found.", "Change Dispensed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Call the backend method to process the physical change
        machine.produceChangeWithoutPurchase();

        // success popup
        JOptionPane.showMessageDialog(this, 
                "Successfully returned PHP " + String.format("%.2f", currentCredit), 
                "Change Dispensed", 
                JOptionPane.INFORMATION_MESSAGE);

        // set credit to 0 and update the text
        gui.setInsertedMoney(0);
        refreshDisplay();
        
        return true;
    }

    /**
    * Refreshes the displayed credit so it matches the current inserted
    * money stored in the main GUI.
    */
    public void refreshDisplay() {
        displayLabel.setText("Current Credit: PHP " + String.format("%.2f", gui.getInsertedMoney()));
    }
}
