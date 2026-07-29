import java.awt.*;
import javax.swing.*;

public class InsertMoneyPanel extends JPanel {
    private String enteredAmount = "";
    private JLabel amountLabel;
   

    public InsertMoneyPanel(VendingMachineGUI gui) {
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

        amountLabel = new JLabel("PHP 0");
        amountLabel.setFont(FontStyle.TITLE);

        display.add(amountLabel);

        centerPanel.add(display);
        centerPanel.add(Box.createVerticalStrut(30));

        JPanel keypad = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        addDigitRow(keypad, gbc, 0, new String[]{"1", "2", "3"});
        addDigitRow(keypad, gbc, 1, new String[]{"4", "5", "6"});
        addDigitRow(keypad, gbc, 2, new String[]{"7", "8", "9"});
        addSingleButtonRow(keypad, gbc, 3, "0");

        keypad.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(keypad);

        add(centerPanel, BorderLayout.CENTER);

        JButton clear = new JButton("Clear");

        clear.addActionListener(e -> {

            enteredAmount = "0";

            amountLabel.setText("PHP 0");

        });

        JButton confirm = new JButton("Confirm");

        // Backend connection insert here later
        confirm.addActionListener(e -> {

            gui.setInsertedMoney(Double.parseDouble(enteredAmount));

            gui.showPanel("Purchase");

        });
        JButton back = new JButton("Back");
        back.addActionListener(e -> gui.showPanel("Purchase"));

        JPanel buttonRow = new JPanel();

        buttonRow.add(clear);

        buttonRow.add(confirm);
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        

        bottom.add(Box.createVerticalStrut(15));
        buttonRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(buttonRow);

        bottom.add(Box.createVerticalStrut(15));

        back.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        button.addActionListener(e -> {

            if(enteredAmount.equals("0")){

                enteredAmount = text;

            }
            else{

            enteredAmount += text;

        }

        amountLabel.setText("PHP " + enteredAmount);

    });

    return button;
    }
}
