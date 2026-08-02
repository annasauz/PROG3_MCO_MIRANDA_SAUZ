import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the menu for accessing Regular Vending Machine features.
 * Extends JPanel to provide purchase, maintenance, and navigation
 * controls.
 */
public class RegularVMMenuPanel extends JPanel {

    /**
    * Creates the Regular Vending Machine menu panel and initializes the
    * navigation buttons for purchasing items, accessing maintenance,
    * and returning to the main menu.
    *
    * @param gui the main vending machine GUI used for panel navigation
    *            and machine selection
    */
    public RegularVMMenuPanel(VendingMachineGUI gui) {

        setBackground(ColorPalette.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(60));

        JLabel title = new JLabel("Regular Vending Machine");

        title.setFont(FontStyle.TITLE);
        title.setForeground(ColorPalette.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(40));

        JButton purchase = createButton("Purchase");
        JButton maintenance = createButton("Maintenance");
        JButton back = createButton("Back");

        purchase.addActionListener(e -> {

            gui.setViewingSpecialMachine(false);
            gui.showPanel("Purchase");

        });

        maintenance.addActionListener(
                e -> gui.showPanel("Maintenance")
        );

        back.addActionListener(
                e -> gui.showPanel("Main")
        );

        add(purchase);
        add(Box.createVerticalStrut(20));

        add(maintenance);
        add(Box.createVerticalStrut(20));

        add(back);
    }

    /**
    * Creates and styles a button used in the Regular Vending Machine menu.
    *
    * @param text the text displayed on the button
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
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        );

        Dimension buttonSize = new Dimension(190, 50);

        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);

        return button;
    }
}
