import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VendingMachineGUI extends JFrame {

    private boolean viewingSpecialMachine = false;
    private CardLayout cardLayout;

    private JPanel cards;

    public VendingMachineGUI() {

        super("Vending Machine Factory");

        cardLayout = new CardLayout();

        cards = new JPanel(cardLayout);

        cards.add(new MainMenu(this), "Main");

        cards.add(new RegularVMMenuPanel(this), "Regular");

        cards.add(new SpecialVMMenuPanel(this), "Special");

        cards.add(new PurchasePanel(this), "Purchase");

        add(cards);

        setSize(950,650);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    public void showPanel(String panelName){
        cardLayout.show(cards,panelName);
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
}