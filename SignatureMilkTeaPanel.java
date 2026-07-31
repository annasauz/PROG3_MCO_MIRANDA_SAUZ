import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SignatureMilkTeaPanel extends JPanel {

    private VendingMachineGUI gui;
    private JTextArea descriptionArea;

    public SignatureMilkTeaPanel(VendingMachineGUI gui) {

        this.gui = gui;

        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("SIGNATURE MILK TEA", JLabel.CENTER);
        title.setFont(FontStyle.TITLE);
        title.setForeground(ColorPalette.TITLE);

        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        centerPanel.add(Box.createVerticalStrut(25));

        JPanel drinkButtons = new JPanel(new GridLayout(3, 1, 15, 15));
        drinkButtons.setOpaque(false);

        JButton oolongButton = createButton("1. Roasted Oolong Matcha Latte");
        JButton earlGreyButton = createButton("2. Classic Brown Sugar Earl Grey Cheese Foam");
        JButton taroButton = createButton("3. Taro Cookie Crunch Green Tea");

        drinkButtons.add(oolongButton);
        drinkButtons.add(earlGreyButton);
        drinkButtons.add(taroButton);

        drinkButtons.setMaximumSize(drinkButtons.getPreferredSize());
        drinkButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(drinkButtons);
        centerPanel.add(Box.createVerticalStrut(25));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(FontStyle.NORMAL);
        descriptionArea.setRows(10);
        descriptionArea.setBorder(BorderFactory.createTitledBorder("Drink Details"));

        descriptionArea.setText(
                "Select a signature milk tea.\n\n"
                + "The recipe details will appear here before purchase."
        );

        centerPanel.add(descriptionArea);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton backButton = createButton("4. Cancel");

        backButton.addActionListener(e -> gui.showPanel("Milk Tea Type"));

        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        oolongButton.addActionListener(e -> purchaseRoastedOolong());
        earlGreyButton.addActionListener(e -> purchaseEarlGrey());
        taroButton.addActionListener(e -> purchaseTaroCookieCrunch());
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(FontStyle.BUTTON);
        button.setBackground(ColorPalette.BUTTON);
        button.setForeground(ColorPalette.BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        return button;
    }

    private void purchaseRoastedOolong() {

        descriptionArea.setText(
                "Roasted Oolong Matcha Latte\n\n"
                + "Tea: Oolong Tea\n"
                + "Milk: Oat Milk\n"
                + "Sweetener: Honey\n"
                + "Sugar: 50%\n"
                + "Ice: Less Ice\n"
                + "Size: Medium\n"
                + "Add-ons: Matcha Powder, Egg Pudding"
        );

        ArrayList<Integer> addons = new ArrayList<>();

        addons.add(SpecialVendingMachine.MATCHA_POWDER);
        addons.add(SpecialVendingMachine.EGG_PUDDING);

        gui.getCustomMilkTeaPanel().purchasePresetMilkTea(
                "Roasted Oolong Matcha Latte",
                SpecialVendingMachine.OOLONG_TEA,
                SpecialVendingMachine.OAT_MILK,
                SpecialVendingMachine.HONEY,
                SpecialVendingMachine.HALF_SUGAR,
                addons,
                SpecialVendingMachine.LESS_ICE,
                SpecialVendingMachine.MEDIUM
        );
    }

        private void purchaseEarlGrey() {

        descriptionArea.setText(
                "Classic Brown Sugar Earl Grey Cheese Foam\n\n"
                + "Tea: Earl Grey Tea\n"
                + "Milk: Whole Milk\n"
                + "Sweetener: Brown Sugar Syrup\n"
                + "Sugar: 50%\n"
                + "Ice: Regular Ice\n"
                + "Size: Large\n"
                + "Add-ons: Tapioca Pearls, Cream Cheese"
        );

        ArrayList<Integer> addons = new ArrayList<>();

        addons.add(SpecialVendingMachine.TAPIOCA_PEARLS);
        addons.add(SpecialVendingMachine.CREAM_CHEESE);

        gui.getCustomMilkTeaPanel().purchasePresetMilkTea(
                "Classic Brown Sugar Earl Grey Cheese Foam",
                SpecialVendingMachine.EARL_GREY_TEA,
                SpecialVendingMachine.WHOLE_MILK,
                SpecialVendingMachine.BROWN_SUGAR_SYRUP,
                SpecialVendingMachine.HALF_SUGAR,
                addons,
                SpecialVendingMachine.REGULAR_ICE,
                SpecialVendingMachine.LARGE
        );
    }

        private void purchaseTaroCookieCrunch() {

        descriptionArea.setText(
                "Taro Cookie Crunch Green Tea\n\n"
                + "Tea: Green Tea\n"
                + "Milk: Whole Milk\n"
                + "Sweetener: Honey\n"
                + "Sugar: 0%\n"
                + "Ice: Regular Ice\n"
                + "Size: Small\n"
                + "Add-ons: Taro Powder, Oreo, Glass Jelly"
        );

        ArrayList<Integer> addons = new ArrayList<>();

        addons.add(SpecialVendingMachine.TARO_POWDER);
        addons.add(SpecialVendingMachine.OREO);
        addons.add(SpecialVendingMachine.GLASS_JELLY);

        gui.getCustomMilkTeaPanel().purchasePresetMilkTea(
                "Taro Cookie Crunch Green Tea",
                SpecialVendingMachine.GREEN_TEA,
                SpecialVendingMachine.WHOLE_MILK,
                SpecialVendingMachine.HONEY,
                SpecialVendingMachine.NO_SUGAR,
                addons,
                SpecialVendingMachine.REGULAR_ICE,
                SpecialVendingMachine.SMALL
        );
    }
}