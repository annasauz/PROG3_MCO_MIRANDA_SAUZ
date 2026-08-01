import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VendingMachineGUI extends JFrame {

    private boolean viewingSpecialMachine = false;
    private CardLayout cardLayout;
    private RegularVendingMachine regularMachine;
    private SpecialVendingMachine specialMachine;
    private InsertMoneyPanel insertMoneyPanel;
    private JPanel cards;
    private PurchasePanel purchasePanel;
    private CustomMilkTeaPanel customMilkTeaPanel;
    private MilkTeaTypePanel milkTeaTypePanel;
    private SignatureMilkTeaPanel signatureMilkTeaPanel;
    private double insertedMoney = 0;

    public VendingMachineGUI() {

        super("Vending Machine Factory");

        cardLayout = new CardLayout();

        cards = new JPanel(cardLayout);

        cards.add(new MainMenu(this), "Main");

        cards.add(new RegularVMMenuPanel(this), "Regular");

        cards.add(new SpecialVMMenuPanel(this), "Special");

        purchasePanel = new PurchasePanel(this);

        cards.add(purchasePanel, "Purchase");

        insertMoneyPanel = new InsertMoneyPanel(this);
        cards.add(insertMoneyPanel, "Insert Money");

        cards.add(new ReturnChangePanel(this), "Return Change");

        milkTeaTypePanel = new MilkTeaTypePanel(this);
        cards.add(milkTeaTypePanel, "Milk Tea Type");

        customMilkTeaPanel = new CustomMilkTeaPanel(this);
        cards.add(customMilkTeaPanel, "Custom Milk Tea");

        signatureMilkTeaPanel = new SignatureMilkTeaPanel(this);
        cards.add(signatureMilkTeaPanel, "Signature Milk Tea");

        add(cards);

        setSize(950,650);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    public void showPanel(String panelName){
    
        
        if(panelName.equals("Purchase")){

            if(viewingSpecialMachine){
                purchasePanel.loadItems(specialMachine);
            }
            else{
                purchasePanel.loadItems(regularMachine);
            }
        purchasePanel.refreshCredit(this);
        purchasePanel.refreshMachineButtons();
        }

        if (panelName.equals("Insert Money")) {
            insertMoneyPanel.refreshDisplay();
        }

        cardLayout.show(cards,panelName);

    }


    public void setRegularMachine(RegularVendingMachine machine){
        regularMachine = machine;
    }

    public RegularVendingMachine getRegularMachine(){
        return regularMachine;
    }

    public void setSpecialMachine(SpecialVendingMachine machine){
        specialMachine = machine;
    }

    public SpecialVendingMachine getSpecialMachine(){
        return specialMachine;
    }
    public JPanel getCards(){
        return cards;
    }

    public void setViewingSpecialMachine(boolean value){
        viewingSpecialMachine = value;
    }

    public boolean isViewingSpecialMachine() {
        return viewingSpecialMachine;
    }

    public void setInsertedMoney(double amount){
        insertedMoney = amount;
    }

    public double getInsertedMoney(){
        return insertedMoney;
    }

    public PurchasePanel getPurchasePanel() {
        return purchasePanel;
    }

    public CustomMilkTeaPanel getCustomMilkTeaPanel() {
        return customMilkTeaPanel;
    }
}

