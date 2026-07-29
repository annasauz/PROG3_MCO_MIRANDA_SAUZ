import javax.swing.*;

public class ReturnChangePanel extends JPanel {


    public ReturnChangePanel(VendingMachineGUI gui) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("RETURN CHANGE");

        title.setFont(FontStyle.TITLE);

        title.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(60));

        add(title);

        add(Box.createVerticalStrut(70));

        JButton returnChange = new JButton("Return Change");

        JButton back = new JButton("Back");

        returnChange.setAlignmentX(CENTER_ALIGNMENT);

        back.setAlignmentX(CENTER_ALIGNMENT);

        add(returnChange);

        add(Box.createVerticalStrut(20));

        add(back);

        back.addActionListener(e -> gui.showPanel("Purchase"));

    }


}
