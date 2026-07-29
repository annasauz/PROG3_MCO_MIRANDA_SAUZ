import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;


public class PurchasePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JLabel creditLabel;
    private VendingMachineGUI gui;

    public PurchasePanel(VendingMachineGUI gui){
        this.gui = gui;
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

        model = new DefaultTableModel(columns, 0){

            @Override
            public boolean isCellEditable(int row, int column){
            return false;
        }

    };

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        //----------------------------------

        JPanel bottom = new JPanel(new FlowLayout());

        JButton insertMoney = new JButton("Insert Money");

        JButton returnChange = new JButton("Return Change");

        JButton back = new JButton("Back");

        creditLabel = new JLabel("Credit: PHP 0.00");
        creditLabel.setFont(FontStyle.NORMAL);
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(creditLabel);

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

    public void loadItems(RegularVendingMachine machine){

        model.setRowCount(0);

        if(machine != null){

            SlotCompartment[] slots = machine.getSlots();

            Item[] items = machine.getItemTemplates();

            for(int i = 0; i < slots.length; i++){

                if(items[i] != null){

                    model.addRow(new Object[]{
                        i + 1,
                        items[i].getName(),
                        "PHP " + String.format("%.2f", items[i].getPrice()),
                        (int) items[i].getCalories(),
                        slots[i].getCurrentInSlotItems()
                    });

                }   

            }

        }

    }

    public void refreshCredit(VendingMachineGUI gui){

        creditLabel.setText(
            "Credit: PHP " + String.format("%.2f", gui.getInsertedMoney())
        );

    }
}
