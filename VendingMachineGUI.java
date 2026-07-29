import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VendingMachineGUI extends JFrame {

    private boolean viewingSpecialMachine = false;
    private CardLayout cardLayout;

    private RegularVendingMachine regularMachine;

    private SpecialVendingMachine specialMachine;

    private JPanel cards;

    private PurchasePanel purchasePanel;

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

        cards.add(new InsertMoneyPanel(this), "Insert Money");

        cards.add(new ReturnChangePanel(this), "Return Change");

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
}
