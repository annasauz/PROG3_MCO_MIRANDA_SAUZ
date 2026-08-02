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

public class MilkTeaTypePanel extends JPanel {

    private VendingMachineGUI gui;

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
