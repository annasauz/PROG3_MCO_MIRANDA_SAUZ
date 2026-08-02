import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Represents the maintenance interface for the active vending machine.
 * Extends JPanel to provide price updates, restocking, change
 * replenishment, cash collection, and machine reports.
 */
public class MaintenanceGuiPanel extends JPanel {
    private static final int[] DENOMINATIONS = {1, 5, 10, 20, 50, 100, 200, 500, 1000};

    private final VendingMachineGUI gui;
    private JLabel machineLabel;

    /**
    * Creates the maintenance panel and initializes its user interface.
    *
    * @param gui the main vending machine GUI used to access the active
    *            vending machine and navigate between panels
    */
    public MaintenanceGuiPanel(VendingMachineGUI gui) {
        this.gui = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadMaintenanceOptions();
    }

    /**
    * Loads the maintenance options into the panel, including buttons for
    * the available maintenance operations based on the active vending
    * machine.
    */
    public void loadMaintenanceOptions() {
        removeAll();
        setBackground(ColorPalette.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        JLabel title = new JLabel("MAINTENANCE MENU");
        title.setFont(FontStyle.TITLE);
        title.setForeground(ColorPalette.TITLE);
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);

        add(Box.createVerticalStrut(10));

        machineLabel = new JLabel("Maintenance on: " + getActiveMachineName());
        machineLabel.setFont(FontStyle.NORMAL);
        machineLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(machineLabel);

        add(Box.createVerticalStrut(30));

        JButton setPriceButton = createButton("Set prices");
        JButton restockButton = createButton("Restock item");
        JButton replenishChangeButton = createButton("Replenish change");
        JButton collectCashButton = createButton("Collect cash");
        JButton printTransactionsButton = createButton("Print Transactions Summary");
        JButton viewChangeInventoryButton = createButton("View Change Inventory");
        JButton machineInsightButton = createButton("Machine Insight");
        JButton backButton = createButton("Back");

        setPriceButton.addActionListener(e -> showSetPriceDialog());
        restockButton.addActionListener(e -> showRestockDialog());
        replenishChangeButton.addActionListener(e -> showReplenishChangeDialog());
        collectCashButton.addActionListener(e -> collectCash());
        printTransactionsButton.addActionListener(e -> printTransactionsSummary());
        viewChangeInventoryButton.addActionListener(e -> showChangeInventoryDialog());
        machineInsightButton.addActionListener(e -> showMachineInsight());
        backButton.addActionListener(e -> {
            if (gui.isViewingSpecialMachine()) {
                gui.showPanel("Special");
            } else {
                gui.showPanel("Regular");
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(200, 300));

        buttonPanel.add(setPriceButton);
        buttonPanel.add(restockButton);
        buttonPanel.add(replenishChangeButton);
        buttonPanel.add(collectCashButton);
        buttonPanel.add(printTransactionsButton);
        buttonPanel.add(viewChangeInventoryButton);

        if (gui.isViewingSpecialMachine()) {
            buttonPanel.add(machineInsightButton);
        }

        buttonPanel.add(backButton);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(buttonPanel);
        add(wrapperPanel);

        revalidate();
        repaint();
    }

    /**
     * Creates a styled JButton with the specified text.
     *
     * @param text The text to display on the button.
     * @return A styled JButton instance.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(FontStyle.BUTTON);
        button.setBackground(ColorPalette.BUTTON);
        button.setForeground(ColorPalette.BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        return button;
    }

    /**
     * Retrieves the currently active vending machine based on the GUI state.
     *
     * @return The active RegularVendingMachine instance, or null if none is active.
     */
    private RegularVendingMachine getActiveMachine() {
        if (gui.isViewingSpecialMachine()) {
            return gui.getSpecialMachine();
        }

        return gui.getRegularMachine();
    }

    /**
     * Determines the name of the currently active vending machine for display purposes.
     *
     * @return A string representing the active machine's name.
     */
    private String getActiveMachineName() {
        if (gui.isViewingSpecialMachine()) {
            return "Special Vending Machine";
        } else {
            return "Regular Vending Machine";
        }
    }

    /**
     * Prompts the user to select a slot number from the vending machine.
     *
     * @param machine     The vending machine to select a slot from.
     * @param title       The title of the prompt dialog.
     * @param actionLabel The label for the action being performed (e.g., "Set Price", "Restock Item").
     * @return The selected slot index (0-based), or -1 if invalid input.
     */
    private int promptSlotSelection(RegularVendingMachine machine, String title, String actionLabel) {

    JOptionPane.showMessageDialog(this,
            machine.getItemNames(),
            "Slot Information",
            JOptionPane.INFORMATION_MESSAGE);

    String input = JOptionPane.showInputDialog(
            this,
            "Select a slot number (1-" + machine.getSlots().length + "):",
            title,
            JOptionPane.QUESTION_MESSAGE);


        try {
            int slot = Integer.parseInt(input.trim());
            if (slot < 1 || slot > machine.getSlots().length) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a slot between 1 and " + machine.getSlots().length + ".",
                        actionLabel,
                        JOptionPane.ERROR_MESSAGE);
                return -1;
            }

            return slot - 1;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a whole number.",
                    actionLabel,
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    /**
     * Prompts the user for a positive integer input.
     *
     * @param message The message to display in the prompt.
     * @param title   The title of the prompt dialog.
     * @return The positive integer entered by the user, or -1 if invalid input.
     */
    private int promptPositiveint(String message, String title) {
        String input = JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);

        try {
            int value = Integer.parseInt(input.trim());
            if (value <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a positive whole number.",
                        title,
                        JOptionPane.ERROR_MESSAGE);
                return -1;
            }

            return value;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid whole number.",
                    title,
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    /**
     * Prompts the user for a positive double input.
     *
     * @param message The message to display in the prompt.
     * @param title   The title of the prompt dialog.
     * @return The positive double entered by the user, or null if invalid input or canceled.
     */
    private Double promptPositiveDouble(String message, String title) {

        String input = JOptionPane.showInputDialog(
            this,
            message,
            title,
            JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) {
            return null;
        }

        try {

            double value = Double.parseDouble(input.trim());

            if (value <= 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a price greater than zero.",
                    title,
                    JOptionPane.ERROR_MESSAGE
                );

                return null;

            } else if (value != Math.floor(value)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a whole-peso price.\n"
                    + "Decimal prices are not supported because\n"
                    + "the machine dispenses whole-peso denominations only.",
                    title,
                    JOptionPane.ERROR_MESSAGE
                );

                return null;
            }

            return value;

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid number.",
                title,
                JOptionPane.ERROR_MESSAGE
            );

            return null;
        }
    }
    /**
     * Prompts the user to select a denomination from the supported denominations.
     *
     * @return The selected denomination, or -1 if invalid input.
     */
    private int promptDenomination() {
        StringBuilder options = new StringBuilder();

        for (int i = 0; i < DENOMINATIONS.length; i++) {
            if (i > 0) {
                options.append(", ");
            }
            options.append(DENOMINATIONS[i]);
        }

        String input = JOptionPane.showInputDialog(
                this,
                "Enter a denomination (" + options + "):",
                "Replenish Change",
                JOptionPane.QUESTION_MESSAGE);

        try {
         if (input == null) {
            return -1;
        }
           int denomination = Integer.parseInt(input.trim());
            for (int allowed : DENOMINATIONS) {
                if (allowed == denomination) {
                    return denomination;
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Please enter one of the supported denominations.",
                    "Replenish Change",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid whole number.",
                    "Replenish Change",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    /**
     * Displays a dialog to set the price of an item in the vending machine.
     * Validates user input and updates the item's price if valid.
     */
    private void showSetPriceDialog() {

        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {

            showMissingMachineError();

        } else {

            int slotIndex = promptSlotSelection(
                machine,
                "Set Price",
                "Set Price");

            if (slotIndex != -1) {

                Item item = machine.getItemTemplates()[slotIndex];

                if (item == null) {

                    JOptionPane.showMessageDialog(
                        this,
                        "That slot is empty.",
                        "Set Price",
                        JOptionPane.ERROR_MESSAGE);

                } else {

                    double currentPrice = item.getPrice();

                    Double price = promptPositiveDouble(
                        "Item: " + item.getName()
                        + "\nCurrent Price: PHP "
                        + String.format("%.2f", currentPrice)
                        + "\n\nEnter the new whole-peso price:",
                        "Set Price");

                    if (price != null) {

                        if (item.setPrice(price)) {

                            JOptionPane.showMessageDialog(
                                this,
                                item.getName()
                                + " price updated successfully.\n"
                                + "Previous Price: PHP "
                                + String.format("%.2f", currentPrice)
                                + "\n"
                                + "New Price: PHP "
                                + String.format("%.2f", price),
                                "Set Price",
                                JOptionPane.INFORMATION_MESSAGE);

                        } else {

                            JOptionPane.showMessageDialog(
                                this,
                                "Unable to update the price.",
                                "Set Price",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }   
            }
        }
    }

    /**
     * Displays a dialog to restock an item in the vending machine.
     * Validates user input and updates the item's stock if valid.
     */
    private void showRestockDialog() {

        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {

            showMissingMachineError();

        } else {

            int slotIndex = promptSlotSelection(
                machine,
                "Restock Item",
                "Restock Item");

            if (slotIndex != -1) {

                Item item = machine.getItemTemplates()[slotIndex];

                if (item == null) {

                    JOptionPane.showMessageDialog(
                        this,
                        "That slot has not been initialized yet.",
                        "Restock Item",
                        JOptionPane.ERROR_MESSAGE);

                } else {

                    SlotCompartment slot = machine.getSlots()[slotIndex];

                    int stockBefore = slot.getCurrentInSlotItems();

                    int maximumStock = slot.getMaximumInSlotItems();

                    int quantity = promptPositiveint(
                        "Item: " + item.getName()
                        + "\nCurrent Stock: "
                        + stockBefore
                        + " / "
                        + maximumStock
                        + "\n\nEnter quantity to add:",
                        "Restock Item");

                    if (quantity != -1) {

                        if (stockBefore >= maximumStock) {

                            JOptionPane.showMessageDialog(
                                this,
                                item.getName()
                                + " is already fully stocked.",
                                "Restock Failed",
                                JOptionPane.ERROR_MESSAGE);

                        } else if (stockBefore + quantity > maximumStock) {

                            int availableSpace = maximumStock - stockBefore;

                            JOptionPane.showMessageDialog(
                                this,
                                "Unable to add "
                                + quantity
                                + " unit(s).\n"
                                + "Available space: "
                                + availableSpace
                                + " unit(s).",
                                "Restock Failed",
                                JOptionPane.ERROR_MESSAGE);

                        } else {

                            machine.restockSlot(
                                slotIndex,
                                item,
                                quantity);

                            int stockAfter = slot.getCurrentInSlotItems();

                            JOptionPane.showMessageDialog(
                                this,
                                item.getName()
                                + " restocked successfully.\n"
                                + "Previous Stock: "
                                + stockBefore
                                + "\n"
                                + "Quantity Added: "
                                + quantity
                                + "\n"
                                + "Current Stock: "
                                + stockAfter
                                + " / "
                                + maximumStock,
                                "Restock Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }
        }
     

    /**
     * Displays a dialog to replenish the change reserves of the vending machine.
     * Validates user input and updates the change reserves if valid.
     */
    private void showReplenishChangeDialog() {

        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {

            showMissingMachineError();

        } else {

            int denomination = promptDenomination();

            if (denomination != -1) {

                int quantity = promptPositiveint(
                    "Enter quantity to add for PHP " + denomination + ":",
                    "Replenish Change");

                if (quantity != -1) {

                    machine.replenishChangeReserves(denomination,quantity);

                    JOptionPane.showMessageDialog(
                        this,
                        "Added " + quantity
                        + " unit(s) of PHP "
                        + denomination
                        + " to the change reserves.",
                        "Replenish Change",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Collects cash from the vending machine's internal cash box and displays the collected amount.
     */
    private void collectCash() {

        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {

            showMissingMachineError();

        } else {

            double collected = machine.getInternalCashBox().getMoneyAmount();

            machine.collectMoney();

            JOptionPane.showMessageDialog(
                this,
                String.format(
                        "Collected PHP %.2f from the machine.",
                        collected
                ),
                "Collect Cash",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Prints the transaction summary of the vending machine to the console.
     */
    private void printTransactionsSummary() {
        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {
            showMissingMachineError();
        } else {
            String report = captureConsoleOutput(() -> machine.printTransactionSummary());
            showReportDialog(report, "Transaction Summary", 650, 470);
        }
    }

    /**
     * Displays a dialog showing the current change inventory of the vending machine.
     */
    private void showChangeInventoryDialog() {

        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {

            showMissingMachineError();

        } else {

            JTextArea output = new JTextArea(buildChangeInventoryReport(machine.getInternalCashBox()));

            output.setEditable(false);
            output.setFont(FontStyle.NORMAL);
            output.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(output);
            scrollPane.setPreferredSize(new Dimension(420, 320));

            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Change Inventory",
                JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Builds a formatted string representing the change inventory of the vending machine.
     *
     * @param cashBox The cash box containing the change inventory.
     * @return A formatted string representing the change inventory.
     */
    private String buildChangeInventoryReport(CashBox cashBox) {

        StringBuilder report = new StringBuilder();

        int[] denominations = cashBox.getDenominations();
        int[] quantities = cashBox.getDenominationsAmount();

        report.append("CHANGE INVENTORY\n");
        report.append("========================================\n\n");

        double totalCash = 0;

        for (int i = denominations.length - 1; i >= 0; i--) {

            int value = denominations[i];
            int quantity = quantities[i];

            report.append(String.format("PHP %-4d : %-3d", value, quantity));

            if (quantity == 0) {

                report.append("   !! OUT OF CHANGE !!");

            } else {

                boolean lowChange = false;

                switch (value) {

                    case 1000:
                    case 500:
                        lowChange = quantity <= 1;
                        break;

                    case 200:
                    case 100:
                        lowChange = quantity <= 2;
                        break;

                    case 50:
                    case 20:
                        lowChange = quantity <= 3;
                        break;

                    case 10:
                    case 5:
                        lowChange = quantity <= 5;
                        break;

                    case 1:
                        lowChange = quantity <= 10;
                        break;
                }

                if (lowChange) {
                    report.append("   !! LOW CHANGE !!");
                }
            }

            report.append("\n");

            totalCash += (double) value * quantity;
        }

        report.append("\n");
        report.append("========================================\n");

        report.append(String.format("Total Change Available: PHP %.2f", totalCash));

        return report.toString();
    }

    /**
     * Displays machine insights for the Special Vending Machine, if applicable.
     */
    private void showMachineInsight() {
        RegularVendingMachine machine = getActiveMachine();

        if (machine == null) {
            showMissingMachineError();
        } else if (machine instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialMachine = (SpecialVendingMachine) machine;
            String report = captureConsoleOutput(() -> specialMachine.printMachineInsights());
            showReportDialog(report, "Machine Insights", 600, 400);
        }
    }

    /**
    * Displays a formatted report inside a scrollable dialog window.
     *
     * @param report the report text to display
    * @param title the dialog window title
    * @param width the preferred width of the dialog
    * @param height the preferred height of the dialog
    */
    private void showReportDialog(String report, String title, int width, int height) {

        JTextArea output = new JTextArea(report);

        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        output.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setPreferredSize(new Dimension(width, height));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            title,
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
    * Captures all text printed to the console while the specified action
    * is executed and returns it as a string.
    *
    * @param action the operation whose console output will be captured
    * @return the captured console output as a string
    */
    private String captureConsoleOutput(Runnable action) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream temporaryOutput = new PrintStream(outputStream);

        try {
        System.setOut(temporaryOutput);
        action.run();
        } finally {
            temporaryOutput.flush();
            System.setOut(originalOutput);
            temporaryOutput.close();
        }

        return outputStream.toString();
    }

    /**
     * Displays an error message indicating that no active vending machine is available.
     */
    private void showMissingMachineError() {
        JOptionPane.showMessageDialog(this,
                "No active vending machine is available.",
                "Maintenance",
                JOptionPane.ERROR_MESSAGE);
    }
}
