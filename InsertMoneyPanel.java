import javax.swing.*;
import java.awt.*;

public class InsertMoneyPanel extends JPanel {

    public InsertMoneyPanel(VendingMachineGUI gui) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        JLabel title = new JLabel("INSERT MONEY", JLabel.CENTER);
        title.setFont(FontStyle.TITLE);
        add(title, BorderLayout.NORTH);


        JPanel keypad = new JPanel(new GridBagLayout());
        JPanel display = new JPanel();
        display.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,250,0,250), BorderFactory.createLineBorder(Color.BLACK, 2)));
        display.setPreferredSize(new Dimension(400, 50));
        display.setAlignmentX(Component.CENTER_ALIGNMENT);
        display.setAlignmentY(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        add(display);

        addDigitRow(keypad, gbc, 0, new String[]{"1", "2", "3"});
        addDigitRow(keypad, gbc, 1, new String[]{"4", "5", "6"});
        addDigitRow(keypad, gbc, 2, new String[]{"7", "8", "9"});
        addSingleButtonRow(keypad, gbc, 3, "0");

        add(keypad, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> gui.showPanel("Purchase"));

        JPanel bottom = new JPanel();
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);
    }

    private void addDigitRow(JPanel panel, GridBagConstraints gbc, int row, String[] digits) {
        for (int col = 0; col < digits.length; col++) {
            addDigitButton(panel, gbc, row, col, digits[col]);
        }
    }

    private void addSingleButtonRow(JPanel panel, GridBagConstraints gbc, int row, String digit) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        panel.add(createDigitButton(digit), gbc);
        gbc.gridwidth = 1;
    }

    private void addDigitButton(JPanel panel, GridBagConstraints gbc, int row, int col, String digit) {
        gbc.gridy = row;
        gbc.gridx = col;
        panel.add(createDigitButton(digit), gbc);
    }

    private JButton createDigitButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(70, 50));
        button.addActionListener(e -> System.out.println("Button " + text + " clicked"));
        return button;
    }
}
