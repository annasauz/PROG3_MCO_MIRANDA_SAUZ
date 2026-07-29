import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PurchasePanel extends JPanel {

    private JTable table;

    public PurchasePanel(VendingMachineGUI gui){

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "PURCHASE ITEMS",
                JLabel.CENTER);

        title.setFont(FontStyle.TITLE);

        add(title, BorderLayout.NORTH);

        //----------------------------------

        String[] columns = {
                "Slot",
                "Item",
                "Price",
                "Calories",
                "Stock"
        };

        table = new JTable(new Object[][]{}, columns);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        //----------------------------------

        JPanel bottom = new JPanel(new FlowLayout());

        JButton insertMoney = new JButton("Insert Money");

        JButton returnChange = new JButton("Return Change");

        JButton back = new JButton("Back");

        bottom.add(insertMoney);

        bottom.add(returnChange);

        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);

        insertMoney.addActionListener(e -> gui.showPanel("Insert Money"));

        returnChange.addActionListener(e -> gui.showPanel("Return Change"));

            back.addActionListener(e -> {

            if(gui.isViewingSpecialMachine()){
                gui.showPanel("Special");
            }
            else{
                gui.showPanel("Regular");
            }

        });

    }

}