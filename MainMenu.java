import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the main menu for selecting a vending machine type.
 * Extends JPanel to provide navigation controls for creating
 * regular or special vending machines, or exiting the application.
 */
public class MainMenu extends JPanel{

    /**
     * Creates the main menu panel and initializes the buttons
     */
    public MainMenu(VendingMachineGUI gui){

        setBackground(ColorPalette.BACKGROUND);

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(60));

        JLabel title = new JLabel("Vending Machine Factory");

        title.setFont(FontStyle.TITLE);

        title.setForeground(ColorPalette.TITLE);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);

        add(Box.createVerticalStrut(70));

        JButton regular = createButton("Create Regular Vending Machine");

        JButton special = createButton("Create Special Vending Machine");

        JButton exit = createButton("Exit");

        // Button Actions
        regular.addActionListener(e -> {

            RegularVendingMachine machine = new RegularVendingMachine();

            gui.setRegularMachine(machine);

            gui.setViewingSpecialMachine(false);

            gui.showPanel("Regular");

        });

        special.addActionListener(e -> {

            SpecialVendingMachine machine = new SpecialVendingMachine();

            gui.setSpecialMachine(machine);

            gui.setViewingSpecialMachine(true);

            gui.showPanel("Special");

        });

        exit.addActionListener(e -> System.exit(0));

        add(regular);

        add(Box.createVerticalStrut(20));

        add(special);

        add(Box.createVerticalStrut(20));

        add(exit);

    }

    private JButton createButton(String text){

        JButton button = new JButton(text);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(FontStyle.BUTTON);

        button.setBackground(ColorPalette.BUTTON);

        button.setForeground(ColorPalette.BUTTON_TEXT);

        button.setFocusPainted(false);

        button.setBorder(BorderFactory.createEmptyBorder(12,30,12,30));

        button.setMaximumSize(button.getPreferredSize());

        return button;

    }

}
