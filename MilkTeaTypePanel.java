import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the interface for selecting a special milk tea type.
 * Extends JPanel to provide custom, signature, and randomized
 * milk tea options.
 */
public class MilkTeaTypePanel extends JPanel {

    private VendingMachineGUI gui;

    /**
    * Creates the milk tea type selection panel and initializes the buttons
    * for custom, signature, randomized, and cancelled milk tea purchases.
    *
    * @param gui the main vending machine GUI used for panel navigation
    *            and access to the active vending machine panels
    */
    public MilkTeaTypePanel(VendingMachineGUI gui) {

        this.gui = gui;
        setBackground(ColorPalette.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(60));

        JLabel title = new JLabel("CHOOSE MILK TEA TYPE");
        title.setFont(FontStyle.TITLE);
        title.setForeground(ColorPalette.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(40));

        JButton customButton = createButton("1. Create Custom Milk Tea");

        JButton signatureButton = createButton("2. Purchase Signature Milk Tea");

        JButton randomButton = createButton("3. Randomized Milk Tea");

        JButton backButton = createButton("4. Cancel");

        customButton.addActionListener(e -> gui.showPanel("Custom Milk Tea"));

        signatureButton.addActionListener(e -> showSignatureMilkTeaWarning());

        randomButton.addActionListener(e -> showRandomMilkTeaWarning());

        backButton.addActionListener(e -> gui.showPanel("Purchase"));
        add(customButton);
        add(Box.createVerticalStrut(20));

        add(signatureButton);
        add(Box.createVerticalStrut(20));

        add(randomButton);
        add(Box.createVerticalStrut(20));

        add(backButton);
    }

    /**
    * Creates and styles a button used in the milk tea type menu.
    *
    * @param text the label displayed on the button
    * @return the configured JButton
    */
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(FontStyle.BUTTON);
        button.setBackground(ColorPalette.BUTTON);
        button.setForeground(ColorPalette.BUTTON_TEXT);
        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        30,
                        12,
                        30
                )
        );

        button.setMaximumSize(
                button.getPreferredSize()
        );

        return button;
    }

    /**
    * Generates a randomized milk tea recipe, including its tea base,
    * milk base, sweetener, sugar level, ice level, cup size, and add-ons.
    * The generated recipe is then submitted for purchase.
    */
    private void purchaseRandomMilkTea() {

        Random random = new Random();

        int tea = random.nextInt(4);
        int milk = getRandomMilk(random.nextInt(4));
        int sweetener = getRandomSweetener(random.nextInt(3));
        int sugarLevel = random.nextInt(3);
        int iceLevel = random.nextInt(4);
        int size = random.nextInt(3) + 1;

        if (sweetener == -1) {
            sugarLevel = SpecialVendingMachine.NO_SUGAR;
        }

        ArrayList<Integer> addons = new ArrayList<>();

        int addonCount = random.nextInt(4);

        while (addons.size() < addonCount) {

            int addon = getRandomAddon(random.nextInt(7) + 1);

            if (addon != -1 && !addons.contains(addon)) {
                addons.add(addon);
            }
        }

        gui.getCustomMilkTeaPanel().purchasePresetMilkTea("Randomized Milk Tea", tea, milk, sweetener, sugarLevel, addons, iceLevel, size);
    }

    /**
    * Converts a randomly generated choice into a milk-base slot constant.
    *
    * @param choice the randomly generated milk choice from 0 to 3
    * @return the slot constant of the corresponding milk base
    */
    private int getRandomMilk(int choice) {

        int milk = SpecialVendingMachine.WHOLE_MILK;

        switch (choice) {
            case 0:
                milk = SpecialVendingMachine.WHOLE_MILK;
                break;

        case 1:
                milk = SpecialVendingMachine.OAT_MILK;
                break;

            case 2:
                milk = SpecialVendingMachine.ALMOND_MILK;
                break;

            case 3:
                milk = SpecialVendingMachine.SKIM_MILK;
                break;
        }

        return milk;
    }

    /**
    * Converts a randomly generated choice into a sweetener slot constant.
    *
    * @param choice the randomly generated sweetener choice from 0 to 2
    * @return the slot constant of the corresponding sweetener,
    *         or -1 when no sweetener is selected
    */
   private int getRandomSweetener(int choice) {

        int sweetener = -1;

        switch (choice) {
            case 0:
                sweetener = -1;
                break;

            case 1:
                sweetener = SpecialVendingMachine.HONEY;
                break;

            case 2:
                sweetener = SpecialVendingMachine.BROWN_SUGAR_SYRUP;
                break;
        }

        return sweetener;
    }

    /**
    * Converts a randomly generated choice into an add-on slot constant.
     *
    * @param choice the randomly generated add-on choice from 1 to 7
    * @return the slot constant of the corresponding add-on,
     *         or -1 when the choice is invalid
     */
    private int getRandomAddon(int choice) {

        int addon = -1;

        switch (choice) {
            case 1:
                addon = SpecialVendingMachine.MATCHA_POWDER;
                break;

            case 2:
                addon = SpecialVendingMachine.TARO_POWDER;
                break;

            case 3:
                addon = SpecialVendingMachine.OREO;
                break;

            case 4:
                addon = SpecialVendingMachine.CREAM_CHEESE;
                break;

            case 5:
                addon = SpecialVendingMachine.TAPIOCA_PEARLS;
                break;

            case 6:
                addon = SpecialVendingMachine.GLASS_JELLY;
                break;

            case 7:
                addon = SpecialVendingMachine.EGG_PUDDING;
                break;
        }

        return addon;
    }

    /**
    * Displays information about signature milk teas and asks the user
    * to confirm before navigating to the signature milk tea panel.
    */
    private void showSignatureMilkTeaWarning() {
        String message = "Signature milk teas have fixed recipes,\n"
                + "cup sizes, and prices.\n\n"
                + "Please review the drink information\n"
                + "before completing your purchase.";

        int choice = JOptionPane.showConfirmDialog(this,
            message,
            "Signature Milk Tea",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE);

        if (choice == JOptionPane.OK_OPTION) {
            gui.showPanel("Signature Milk Tea");
        }
    }

    /**
    * Displays a warning that the milk tea recipe, size, and price will be
    * randomly generated, then proceeds to generate and purchase the drink.
    */
    private void showRandomMilkTeaWarning() {

        JOptionPane.showMessageDialog(
            this,
            "The recipe, cup size, and total price\n"
            + "are randomly generated.\n\n"
            + "If your inserted credit is insufficient,\n"
            + "the purchase will not proceed.",
            "Randomized Milk Tea",
            JOptionPane.WARNING_MESSAGE);

        purchaseRandomMilkTea();
    }

}
