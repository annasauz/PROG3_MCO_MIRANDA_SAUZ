import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class CustomMilkTeaPanel extends JPanel {

    private VendingMachineGUI gui;

    private JComboBox<String> teaComboBox;
    private JComboBox<String> milkComboBox;
    private JComboBox<String> sweetenerComboBox;

    private JRadioButton noSugarButton;
    private JRadioButton halfSugarButton;
    private JRadioButton fullSugarButton;

    private JRadioButton noIceButton;
    private JRadioButton lessIceButton;
    private JRadioButton regularIceButton;
    private JRadioButton extraIceButton;

    private JRadioButton smallButton;
    private JRadioButton mediumButton;
    private JRadioButton largeButton;

    private JCheckBox matchaCheckBox;
    private JCheckBox taroCheckBox;
    private JCheckBox oreoCheckBox;
    private JCheckBox creamCheeseCheckBox;
    private JCheckBox tapiocaCheckBox;
    private JCheckBox glassJellyCheckBox;
    private JCheckBox eggPuddingCheckBox;

    private JButton purchaseButton;
    private JLabel priceLabel;
    private JLabel caloriesLabel;

    /**
    * Creates the Custom Milk Tea panel and initializes all controls for
    * selecting ingredients, customizing the drink, and purchasing a
    * custom milk tea.
    *
    * @param gui the main vending machine GUI used for navigation and
    *            access to the active Special Vending Machine
     */
    public CustomMilkTeaPanel(VendingMachineGUI gui) {

        this.gui = gui;

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
            "BUILD CUSTOM MILK TEA",
            JLabel.CENTER
        );

        title.setFont(FontStyle.TITLE);

        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();

        formPanel.setLayout(
            new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );

        formPanel.setBorder(
            BorderFactory.createEmptyBorder(
                20,
                40,
                20,
                40
            )
        );

        addComboBoxSections(formPanel);
        addSizeSection(formPanel);
        addSugarSection(formPanel);

        sweetenerComboBox.addActionListener(e -> sugarLevelHandler());
        addSugarSizeListeners();
        sugarLevelHandler();

        addIceSection(formPanel);
        addAddonsSection(formPanel);
     
        JScrollPane scrollPane =
            new JScrollPane(formPanel);

        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        addBottomButtons();
    }

    /**
    * Creates and adds the combo box sections for selecting the tea base,
    * milk base, and sweetener.
    *
    * @param formPanel the panel to which the combo box components are added
    */
    private void addComboBoxSections(JPanel formPanel) {

        teaComboBox = new JComboBox<>(new String[] {
            "Black Tea",
            "Green Tea",
            "Earl Grey Tea",
            "Oolong Tea"
        });

        milkComboBox = new JComboBox<>(new String[] {
            "Whole Milk",
            "Oat Milk",
            "Almond Milk",
            "Skim Milk"
        });

        sweetenerComboBox = new JComboBox<>(new String[] {
            "None",
            "Honey",
            "Brown Sugar Syrup"
        });

        formPanel.add(
            createComboBoxPanel(
                "Tea Base",
                teaComboBox
            )
        );

        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(
            createComboBoxPanel(
                "Milk",
                milkComboBox
            )
        );

        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(
            createComboBoxPanel(
                "Sweetener",
                sweetenerComboBox
            )
        );

        formPanel.add(Box.createVerticalStrut(20));
    }

    /**
    * Creates a labeled combo box panel used for selecting an ingredient.
    *
    * @param labelText the text displayed beside the combo box
     * @param comboBox the combo box containing the available options
    * @return the configured combo box panel
    */
    private JPanel createComboBoxPanel(String labelText, JComboBox<String> comboBox) {

        JPanel panel = new JPanel(
            new BorderLayout(15, 0)
        );

        JLabel label = new JLabel(labelText);

        label.setFont(FontStyle.NORMAL);

        comboBox.setFont(FontStyle.NORMAL);

        panel.add(label, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.CENTER);

        panel.setMaximumSize(
            panel.getPreferredSize()
        );

        panel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        return panel;
    }

    /**
    * Creates and adds the sugar level selection controls to the form.
    *
    * @param formPanel the panel to which the sugar controls are added
     */
    private void addSugarSection(JPanel formPanel) {

        JPanel sugarPanel = new JPanel(
            new GridLayout(2, 3, 10, 10)
        );

        JLabel sugarTitle =
            new JLabel("Sugar Level");

        sugarTitle.setFont(FontStyle.NORMAL);

        noSugarButton =
            new JRadioButton("0%");

        halfSugarButton =
            new JRadioButton("50%");

        fullSugarButton =
            new JRadioButton("100%");

        ButtonGroup sugarGroup =
            new ButtonGroup();

        sugarGroup.add(noSugarButton);
        sugarGroup.add(halfSugarButton);
        sugarGroup.add(fullSugarButton);

        fullSugarButton.setSelected(true);

        sugarPanel.add(sugarTitle);
        sugarPanel.add(new JLabel());
        sugarPanel.add(new JLabel());

        sugarPanel.add(noSugarButton);
        sugarPanel.add(halfSugarButton);
        sugarPanel.add(fullSugarButton);

        formPanel.add(sugarPanel);
        formPanel.add(Box.createVerticalStrut(20));
    }

    /**
    * Creates and adds the ice level selection controls to the form.
    *
    * @param formPanel the panel to which the ice controls are added
    */
    private void addIceSection(JPanel formPanel) {

        JPanel icePanel = new JPanel(
            new GridLayout(2, 4, 10, 10)
        );

        JLabel iceTitle =
            new JLabel("Ice Level");

        iceTitle.setFont(FontStyle.NORMAL);

        noIceButton =
            new JRadioButton("No Ice");

        lessIceButton =
            new JRadioButton("Less Ice");

        regularIceButton =
            new JRadioButton("Regular Ice");

        extraIceButton =
            new JRadioButton("Extra Ice");

        ButtonGroup iceGroup =
            new ButtonGroup();

        iceGroup.add(noIceButton);
        iceGroup.add(lessIceButton);
        iceGroup.add(regularIceButton);
        iceGroup.add(extraIceButton);

        regularIceButton.setSelected(true);

        icePanel.add(iceTitle);
        icePanel.add(new JLabel());
        icePanel.add(new JLabel());
        icePanel.add(new JLabel());

        icePanel.add(noIceButton);
        icePanel.add(lessIceButton);
        icePanel.add(regularIceButton);
        icePanel.add(extraIceButton);

        formPanel.add(icePanel);
        formPanel.add(Box.createVerticalStrut(20));
    }

    /**
    * Creates and adds the cup size selection controls to the form.
    *
    * @param formPanel the panel to which the size controls are added
    */
    private void addSizeSection(JPanel formPanel) {

        JPanel sizePanel = new JPanel(
            new GridLayout(2, 3, 10, 10)
        );

        JLabel sizeTitle =
            new JLabel("Size");

        sizeTitle.setFont(FontStyle.NORMAL);

        smallButton =
            new JRadioButton("Small");

        mediumButton =
            new JRadioButton("Medium");

        largeButton =
            new JRadioButton("Large");

        ButtonGroup sizeGroup =
            new ButtonGroup();

        sizeGroup.add(smallButton);
        sizeGroup.add(mediumButton);
        sizeGroup.add(largeButton);

        mediumButton.setSelected(true);

        sizePanel.add(sizeTitle);
        sizePanel.add(new JLabel());
        sizePanel.add(new JLabel());

        sizePanel.add(smallButton);
        sizePanel.add(mediumButton);
        sizePanel.add(largeButton);

        formPanel.add(sizePanel);
        formPanel.add(Box.createVerticalStrut(20));
    }

    /**
    * Creates and adds the available add-on ingredient check boxes to
    * the customization form.
    *
    * @param formPanel the panel to which the add-on controls are added
    */
    private void addAddonsSection(JPanel formPanel) {

        JPanel addonsPanel = new JPanel(
            new GridLayout(4, 2, 10, 10)
        );

        addonsPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Add-ons"
            )
        );

        matchaCheckBox =
            new JCheckBox("Matcha Powder");

        taroCheckBox =
            new JCheckBox("Taro Powder");

        oreoCheckBox =
            new JCheckBox("Oreo");

        creamCheeseCheckBox =
            new JCheckBox("Cream Cheese");

        tapiocaCheckBox =
            new JCheckBox("Tapioca Pearls");

        glassJellyCheckBox =
            new JCheckBox("Glass Jelly");

        eggPuddingCheckBox =
            new JCheckBox("Egg Pudding");

        addonsPanel.add(matchaCheckBox);
        addonsPanel.add(taroCheckBox);
        addonsPanel.add(oreoCheckBox);
        addonsPanel.add(creamCheeseCheckBox);
        addonsPanel.add(tapiocaCheckBox);
        addonsPanel.add(glassJellyCheckBox);
        addonsPanel.add(eggPuddingCheckBox);

        formPanel.add(addonsPanel);
        formPanel.add(Box.createVerticalStrut(20));
    }

    /**
    * Creates and adds the purchase and navigation buttons for the
    * custom milk tea panel.
    */
    private void addBottomButtons() {

        JPanel bottomPanel = new JPanel();

        purchaseButton = new JButton("Purchase Custom Milk Tea");

        JButton backButton = new JButton("Back");

        purchaseButton.setFont(FontStyle.BUTTON);
        backButton.setFont(FontStyle.BUTTON);

        purchaseButton.addActionListener(e -> {

        boolean purchaseSuccessful = processCustomMilkTeaPurchase();

        if (purchaseSuccessful) {
            System.out.println("Custom milk tea transaction completed.");
        } else {
            System.out.println("Custom milk tea transaction was not completed.");
            }
        });

        backButton.addActionListener(e ->
            gui.showPanel("Purchase")
        );

        bottomPanel.add(purchaseButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
    * Retrieves the list of selected add-on ingredients.
    *
    * @return an ArrayList containing the selected add-on slot constants
    */
    public ArrayList<Integer> getSelectedAddons() {

        ArrayList<Integer> addons =
            new ArrayList<>();

        if (matchaCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.MATCHA_POWDER
            );
        }

        if (taroCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.TARO_POWDER
            );
        }

        if (oreoCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.OREO
            );
        }

        if (creamCheeseCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.CREAM_CHEESE
            );
        }

        if (tapiocaCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.TAPIOCA_PEARLS
            );
        }

        if (glassJellyCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.GLASS_JELLY
            );
        }

        if (eggPuddingCheckBox.isSelected()) {
            addons.add(
                SpecialVendingMachine.EGG_PUDDING
            );
        }

        return addons;
    }

    /**
    * Returns the selected tea base.
    *
    * @return the selected tea constant
    */
    private int getSelectedTea() {

        int selectedIndex = teaComboBox.getSelectedIndex();

        int tea = SpecialVendingMachine.BLACK_TEA;

        switch (selectedIndex) {

            case 0:
                tea = SpecialVendingMachine.BLACK_TEA;
                break;

            case 1:
                tea = SpecialVendingMachine.GREEN_TEA;
                break;

            case 2:
                tea = SpecialVendingMachine.EARL_GREY_TEA;
                break;

            case 3:
                tea = SpecialVendingMachine.OOLONG_TEA;
                break;
        }

        return tea;
    }

    /**
    * Returns the selected milk base.
     *
    * @return the selected milk constant
     */
    private int getSelectedMilk() {

        int selectedIndex = milkComboBox.getSelectedIndex();

        int milk = SpecialVendingMachine.WHOLE_MILK;

        switch (selectedIndex) {

            case 0:
                milk = SpecialVendingMachine.WHOLE_MILK;
                break;

            case 1:
                milk = SpecialVendingMachine.OAT_MILK;
                break;

            case 2:
                milk = SpecialVendingMachine.ALMOND_MILK;
                break;

            case 3:
                milk = SpecialVendingMachine.SKIM_MILK;
                break;
        }

        return milk;
    }
    
    /**
    * Returns the selected sweetener.
    *
    * @return the selected sweetener constant, or -1 if no sweetener
    *         is selected
    */
    private int getSelectedSweetener() {

        int selectedIndex = sweetenerComboBox.getSelectedIndex();

        int sweetener = -1;

        switch (selectedIndex) {

            case 0:
                sweetener = -1;
                break;

            case 1:
                sweetener = SpecialVendingMachine.HONEY;
                break;

            case 2:
                sweetener = SpecialVendingMachine.BROWN_SUGAR_SYRUP;
                break;
        }

        return sweetener;
    }

    /**
    * Returns the selected sugar level.
    *
    * @return the selected sugar level constant
    */
    private int getSelectedSugarLevel() {

        int sugarLevel = SpecialVendingMachine.FULL_SUGAR;

        if (noSugarButton.isSelected()) {

            sugarLevel = SpecialVendingMachine.NO_SUGAR;

        } else if (halfSugarButton.isSelected()) {

            sugarLevel = SpecialVendingMachine.HALF_SUGAR;

        } else if (fullSugarButton.isSelected()) {

            sugarLevel = SpecialVendingMachine.FULL_SUGAR;
        }

        return sugarLevel;
    }

    /**
    * Returns the selected ice level.
    *  
    * @return the selected ice level constant
    */
    private int getSelectedIceLevel() {

        int iceLevel = SpecialVendingMachine.REGULAR_ICE;

        if (noIceButton.isSelected()) {

            iceLevel = SpecialVendingMachine.NO_ICE;

        } else if (lessIceButton.isSelected()) {

            iceLevel = SpecialVendingMachine.LESS_ICE;

        } else if (regularIceButton.isSelected()) {

            iceLevel = SpecialVendingMachine.REGULAR_ICE;

        } else if (extraIceButton.isSelected()) {

            iceLevel = SpecialVendingMachine.EXTRA_ICE;
        }

        return iceLevel;
    }

    /**
    * Returns the selected cup size.
    *
    * @return the selected size constant
    */
    private int getSelectedSize() {

        int size = SpecialVendingMachine.MEDIUM;

        if (smallButton.isSelected()) {

           size = SpecialVendingMachine.SMALL;

        } else if (mediumButton.isSelected()) {

            size = SpecialVendingMachine.MEDIUM;

        } else if (largeButton.isSelected()) {

            size = SpecialVendingMachine.LARGE;
        }

        return size;
    }

    /**
    * Builds a summary of the customer's selected custom milk tea
    * configuration before purchase confirmation.
    *
     * @return a formatted order summary
    */
    private String buildOrderSummary() {

        StringBuilder summary = new StringBuilder();

        summary.append("========== ORDER SUMMARY ==========\n");

        summary.append("Tea Base: ").append(teaComboBox.getSelectedItem()).append("\n");

        summary.append("Milk Base: ").append(milkComboBox.getSelectedItem()).append("\n");

        summary.append("Sweetener: ").append(sweetenerComboBox.getSelectedItem()).append("\n");

        summary.append("Sugar Level: ");

        if (noSugarButton.isSelected()) {
            summary.append("0%");
        } else if (halfSugarButton.isSelected()) {
            summary.append("50%");
        } else {
            summary.append("100%");
        }

        summary.append("\n");

        summary.append("Ice Level: ");

        if (noIceButton.isSelected()) {
            summary.append("No Ice");
        } else if (lessIceButton.isSelected()) {
            summary.append("Less Ice");
        } else if (regularIceButton.isSelected()) {
            summary.append("Regular Ice");
        } else {
            summary.append("Extra Ice");
        }

        summary.append("\n");

        summary.append("Size: ");

        if (smallButton.isSelected()) {
            summary.append("Small");
        } else if (mediumButton.isSelected()) {
            summary.append("Medium");
        } else {
            summary.append("Large");
        }

        summary.append("\nAdd-ons:\n");

        boolean hasAddons = false;

        if (matchaCheckBox.isSelected()) {
            summary.append("- Matcha Powder\n");
            hasAddons = true;
        }

        if (taroCheckBox.isSelected()) {
            summary.append("- Taro Powder\n");
            hasAddons = true;
        }

        if (oreoCheckBox.isSelected()) {
            summary.append("- Oreo\n");
            hasAddons = true;
        }

        if (creamCheeseCheckBox.isSelected()) {
            summary.append("- Cream Cheese\n");
            hasAddons = true;
        }

        if (tapiocaCheckBox.isSelected()) {
            summary.append("- Tapioca Pearls\n");
            hasAddons = true;
        }

        if (glassJellyCheckBox.isSelected()) {
            summary.append("- Glass Jelly\n");
            hasAddons = true;
        }

        if (eggPuddingCheckBox.isSelected()) {
            summary.append("- Egg Pudding\n");
            hasAddons = true;
        }

        if (!hasAddons) {
            summary.append("- None\n");
        }

        summary.append("===================================");

        return summary.toString();
    }

    /**
    * Processes the purchase of the currently configured custom milk tea,
     * including confirmation, preparation, transaction processing,
     * nutrition display, and receipt generation.
     *
     * @return true if the purchase is successful; false otherwise
    */
    private boolean processCustomMilkTeaPurchase() {

        SpecialVendingMachine machine = gui.getSpecialMachine();

        boolean purchaseSuccessful = false;

        if (machine == null) {

            JOptionPane.showMessageDialog(this,
            "No active Special Vending Machine found.",
            "Machine Error",
            JOptionPane.ERROR_MESSAGE);

        } else {

            String orderSummary =
                buildOrderSummary();

            int confirmation =JOptionPane.showConfirmDialog(this, orderSummary
                + "\n\nProceed with this purchase?",
                "Confirm Custom Milk Tea",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {

                int tea = getSelectedTea();

                int milk = getSelectedMilk();

                int sweetener = getSelectedSweetener();

                int sugarLevel = getSelectedSugarLevel();

                ArrayList<Integer> addons = getSelectedAddons();

                int iceLevel = getSelectedIceLevel();

                int size = getSelectedSize();

                double creditBeforePurchase = machine.transactionCashBox.getMoneyAmount();
                purchaseSuccessful = machine.purchaseCustomMilkTea(tea, milk, sweetener, sugarLevel, addons, iceLevel, size);

                if (purchaseSuccessful) {

                    gui.setInsertedMoney(machine.transactionCashBox.getMoneyAmount());

                    ArrayList<String> preparationSteps = buildPreparationSteps(machine, tea, milk, sweetener, sugarLevel, addons, iceLevel, size);

                    showPreparationAnimation(preparationSteps, new Runnable() {

                    @Override
                    public void run() {

                        showTransactionComplete(machine, size);

                        showNutritionFacts(tea, milk, sugarLevel, iceLevel, addons, machine.getLastCalories());

                        gui.getPurchasePanel().showFeedbackAndReceipt(machine);

                        gui.showPanel("Purchase");
                        }
                    });
                } else {

                    gui.setInsertedMoney(machine.transactionCashBox.getMoneyAmount());

                    showMilkTeaPurchaseError(machine, tea, milk, sweetener, sugarLevel, addons, iceLevel, size, creditBeforePurchase);
                }
            }
        }
        return purchaseSuccessful;
    }

    /**
    * Builds the preparation steps displayed during the custom milk tea
    * preparation animation.
    *
    * @param machine the active special vending machine
    * @param tea the selected tea base
    * @param milk the selected milk base
    * @param sweetener the selected sweetener
    * @param sugarLevel the selected sugar level
    * @param addons the selected add-ons
    * @param iceLevel the selected ice level
    * @param size the selected cup size
    * @return an ordered list of preparation steps
    */
    private ArrayList<String> buildPreparationSteps(SpecialVendingMachine machine, int tea, int milk, int sweetener, int sugarLevel, ArrayList<Integer> addons, int iceLevel, int size) {

        ArrayList<String> steps = new ArrayList<>();

        int multiplier = getSizeMultiplier(size);
        int sweetenerServings = getSweetenerServings(sugarLevel, multiplier);
        int iceServings = getIceServings(iceLevel);

        String teaName = machine.getItemTemplates()[tea].getName();
        String milkName = machine.getItemTemplates()[milk].getName();

        steps.add("--- PREPARING CUSTOM MILK TEA ---");

        for (int i = 0; i < multiplier; i++) {
            if (i == 0) {
            steps.add("Brewing " + teaName);
            } else {
                steps.add("...Adding extra serving of " + teaName);
            }
        }

        for (int i = 0; i < multiplier; i++) {
            if (i == 0) {
                steps.add("...Pouring " + milkName);
            } else {
                steps.add("...Adding extra serving of " + milkName);
            }
        }

        if (sweetener != -1) {

            String sweetenerName = machine.getItemTemplates()[sweetener].getName();
            String sugarText = getSugarText(sugarLevel);

            for (int i = 0; i < sweetenerServings; i++) {
                if (i == 0) {
                    steps.add("...Adding " + sweetenerName + " (" + sugarText + ")");
                } else {
                    steps.add("...Adding extra serving of " + sweetenerName);
                }
            }
        }

        for (int addon : addons) {

            String addonName = machine.getItemTemplates()[addon].getName();

            for (int i = 0; i < multiplier; i++) {
                if (i == 0) {
                    steps.add("...Adding " + addonName);
                } else {
                    steps.add("...Adding extra serving of " + addonName);
                }
            }
        }

        switch (iceLevel) {
            case SpecialVendingMachine.NO_ICE:
            break;

            case SpecialVendingMachine.LESS_ICE:
            steps.add("...Adding Less Ice");
            break;

        case SpecialVendingMachine.REGULAR_ICE:
            steps.add("...Adding Regular Ice");
            break;

        case SpecialVendingMachine.EXTRA_ICE:
            steps.add("...Adding Extra Ice");
            break;
        }

        steps.add("...Shaking and sealing cup");
        steps.add("...Milk Tea Done!");

        return steps;
    }
    
    /**
    * Returns the ingredient multiplier based on the selected cup size.
    *
    * @param size the selected cup size
    * @return the ingredient multiplier
    */
    private int getSizeMultiplier(int size) {

        int multiplier = 1;

        switch (size) {
            case SpecialVendingMachine.SMALL:
                multiplier = 1;
                break;

            case SpecialVendingMachine.MEDIUM:
                multiplier = 2;
                break;

            case SpecialVendingMachine.LARGE:
                multiplier = 3;
                break;
        }

        return multiplier;
    }

    /**
    * Determines the number of sweetener servings required based on the
    * selected sugar level and cup size.
    *
    * @param sugarLevel the selected sugar level
    * @param multiplier the ingredient multiplier for the selected size
    * @return the number of sweetener servings
    */
    private int getSweetenerServings(int sugarLevel, int multiplier) {

        int servings = multiplier;

        switch (sugarLevel) {
            case SpecialVendingMachine.NO_SUGAR:
                servings = 0;
                break;

            case SpecialVendingMachine.HALF_SUGAR:
                servings = (multiplier + 1) / 2;
                break;

            case SpecialVendingMachine.FULL_SUGAR:
                servings = multiplier;
                break;
        }

        return servings;
    }

    /**
    * Determines the number of ice servings required for the selected
    * ice level.
    *
    * @param iceLevel the selected ice level
     * @return the number of ice servings
     */
    private int getIceServings(int iceLevel) {

        int servings = 3;

        switch (iceLevel) {
            case SpecialVendingMachine.NO_ICE:
                servings = 0;
                break;

            case SpecialVendingMachine.LESS_ICE:
                servings = 1;
                break;

            case SpecialVendingMachine.REGULAR_ICE:
                servings = 2;
                break;

            case SpecialVendingMachine.EXTRA_ICE:
                servings = 3;
                break;
        }

        return servings;
        }

    /**
    * Returns the display text corresponding to the selected sugar level.
    *
    * @param sugarLevel the selected sugar level
    * @return the sugar level as display text
    */
        private String getSugarText(int sugarLevel) {

            String sugarText = "100%";

            switch (sugarLevel) {
            case SpecialVendingMachine.NO_SUGAR:
                sugarText = "0%";
                break;

            case SpecialVendingMachine.HALF_SUGAR:
                sugarText = "50%";
                break;

            case SpecialVendingMachine.FULL_SUGAR:
                sugarText = "100%";
                break;
        }

        return sugarText;
    }

    /**
    * Displays an animated preparation sequence for the custom milk tea
    * before completing the transaction.
    *
    * @param steps the ordered preparation steps to display
    * @param afterAnimation the action to execute after the animation
    *                       finishes
    */
    private void showPreparationAnimation(ArrayList<String> steps, Runnable afterAnimation) {

        JDialog dialog = new JDialog();
        dialog.setTitle("Preparing Custom Milk Tea");
        dialog.setModal(true);
        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JTextArea preparationArea = new JTextArea();
        preparationArea.setEditable(false);
        preparationArea.setFocusable(false);
        preparationArea.setFont(FontStyle.NORMAL);
        preparationArea.setLineWrap(true);
        preparationArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(preparationArea);
        dialog.add(scrollPane);

        final int[] currentStep = {0};

        Timer timer = new Timer(350, null);

        timer.addActionListener(e -> {

        if (currentStep[0] < steps.size()) {

            preparationArea.append(steps.get(currentStep[0]) + "\n");
            preparationArea.setCaretPosition(preparationArea.getDocument().getLength());
            currentStep[0]++;

        } else {

            timer.stop();
            dialog.dispose();
            }
        });

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                afterAnimation.run();
            }
        });

        timer.start();
        dialog.setVisible(true);
    }

    /**
    * Displays the completed transaction details, including cup size,
    * total price, calories, and change dispensed.
    *
    * @param machine the special vending machine containing the completed
    *                transaction information
    * @param size the purchased cup size
    */
    private void showTransactionComplete(SpecialVendingMachine machine, int size) {

        String sizeText = getSizeText(size);

        String message = "--- TRANSACTION COMPLETE ---\n\n"
            + "Cup Size: " + sizeText + "\n"
            + "Total Price: PHP " + String.format("%.2f", machine.getLastPurchasePrice()) + "\n"
            + "Total Calories: " + String.format("%.0f", machine.getLastCalories()) + " kcal\n"
            + "Total Change Dispensed: PHP " + String.format("%.2f", machine.getLastChange());

        JOptionPane.showMessageDialog(this, message, "Transaction Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
    * Returns the display text for the specified cup size.
    *
    * @param size the selected cup size
    * @return the cup size as display text
    */
    private String getSizeText(int size) {

        String sizeText = "Unknown";

        switch (size) {
            case SpecialVendingMachine.SMALL:
                sizeText = "Small";
                break;

            case SpecialVendingMachine.MEDIUM:
                sizeText = "Medium";
                break;

            case SpecialVendingMachine.LARGE:
                sizeText = "Large";
                break;
        }

        return sizeText;
    }

    /**
    * Displays the nutrition information and allergen summary for the
    * completed milk tea purchase.
    *
    * @param tea the selected tea base
    * @param milk the selected milk base
    * @param sugarLevel the selected sugar level
    * @param iceLevel the selected ice level
    * @param addons the selected add-ons
    * @param totalCalories the total calories of the drink
    */
    private void showNutritionFacts(int tea, int milk, int sugarLevel, int iceLevel, ArrayList<Integer> addons, double totalCalories) {

        StringBuilder facts = new StringBuilder();

        facts.append("========== NUTRITION FACTS ==========\n");
        facts.append("Calories : ").append(String.format("%.0f", totalCalories)).append(" kcal\n\n");

        facts.append("Contains:\n");
        facts.append("[X] Caffeine\n");

        if (milk == SpecialVendingMachine.WHOLE_MILK || milk == SpecialVendingMachine.SKIM_MILK) {
            facts.append("[X] Dairy\n");
        }

        facts.append("\nSugar Level:\n");
        facts.append("[X] ").append(getSugarText(sugarLevel)).append("\n\n");

        facts.append("Allergens:\n");

        boolean allergenFound = false;

        if (milk == SpecialVendingMachine.WHOLE_MILK || milk == SpecialVendingMachine.SKIM_MILK) {
            facts.append("[X] Milk\n");
            allergenFound = true;
        }

        if (milk == SpecialVendingMachine.ALMOND_MILK) {
            facts.append("[X] Tree Nuts\n");
            allergenFound = true;
        }

        if (addons.contains(SpecialVendingMachine.OREO)) {
            facts.append("[X] Gluten\n");
            allergenFound = true;
        }

        if (!allergenFound) {
            facts.append("[X] None\n");
        }

        facts.append("\nNutrition Notes:\n");

        switch (sugarLevel) {
            case SpecialVendingMachine.NO_SUGAR:
                facts.append("[X] No Added Sugar\n");
                break;

            case SpecialVendingMachine.HALF_SUGAR:
                facts.append("[X] Moderate Sugar\n");
                break;

            case SpecialVendingMachine.FULL_SUGAR:
                facts.append("[X] High Sugar\n");
                break;
        }

        if (totalCalories < 250) {
            facts.append("[X] Low Calorie\n");
        } else if (totalCalories < 500) {
            facts.append("[X] Moderate Calorie\n");
        } else {
            facts.append("[X] High Calorie\n");
        }

        if (milk == SpecialVendingMachine.OAT_MILK || milk == SpecialVendingMachine.ALMOND_MILK) {
            facts.append("[X] Dairy-Free\n");
        } else {
            facts.append("[X] Contains Dairy\n");
        }

        if (getIceServings(iceLevel) > 0) {
            facts.append("[X] Served Cold\n");
        }

        facts.append("[X] Vegetarian\n");
        facts.append("====================================");

        JTextArea factsArea = new JTextArea(facts.toString());
        factsArea.setEditable(false);
        factsArea.setFocusable(false);
        factsArea.setFont(FontStyle.NORMAL);
        factsArea.setRows(20);
        factsArea.setColumns(36);   

        JOptionPane.showMessageDialog(this, new JScrollPane(factsArea), "Nutrition Facts", JOptionPane.PLAIN_MESSAGE);
    }

     /**
    * Processes the purchase of a preset milk tea recipe, such as a
    * signature or randomized milk tea. It submits the fixed ingredients
    * to the Special Vending Machine, displays the preparation animation,
    * shows the transaction and nutrition details, and requests customer
    * feedback and a receipt when successful.
    *
    * @param drinkName the name of the preset milk tea
    * @param tea the slot index of the selected tea base
    * @param milk the slot index of the selected milk base
    * @param sweetener the slot index of the selected sweetener,
    *                  or -1 if no sweetener is used
    * @param sugarLevel the selected sugar-level constant
     * @param addons the slot indices of the selected add-ons
    * @param iceLevel the selected ice-level constant
    * @param size the selected cup-size constant
     * @return true if the preset milk tea purchase is completed successfully;
     *         false otherwise
    */
    public boolean purchasePresetMilkTea(String drinkName, int tea, int milk, int sweetener, int sugarLevel, ArrayList<Integer> addons, int iceLevel, int size) {

        SpecialVendingMachine machine = gui.getSpecialMachine();
        boolean purchaseSuccessful = false;

        if (machine == null) {

            JOptionPane.showMessageDialog(this, "No active Special Vending Machine found.", "Machine Error", JOptionPane.ERROR_MESSAGE);

        } else {

            double creditBeforePurchase = machine.transactionCashBox.getMoneyAmount();
            purchaseSuccessful = machine.purchaseCustomMilkTea(tea, milk, sweetener, sugarLevel, addons, iceLevel, size);

            if (purchaseSuccessful) {

                gui.setInsertedMoney(machine.transactionCashBox.getMoneyAmount());

                ArrayList<String> preparationSteps =
                    buildPreparationSteps(machine, tea, milk, sweetener, sugarLevel,addons,
                            iceLevel,
                            size
                    );

            showPreparationAnimation(
                    preparationSteps,
                    new Runnable() {
                        @Override
                        public void run() {

                            showTransactionComplete(machine, size);

                            showNutritionFacts(
                                    tea,
                                    milk,
                                    sugarLevel,
                                    iceLevel,
                                    addons,
                                    machine.getLastCalories()
                            );

                            gui.getPurchasePanel()
                                    .showFeedbackAndReceipt(machine);

                            gui.showPanel("Purchase");
                        }
                    }
            );

        } else {

            gui.setInsertedMoney(
                    machine.transactionCashBox.getMoneyAmount()
            );

            showMilkTeaPurchaseError(machine, tea, milk, sweetener, sugarLevel, addons, iceLevel, size, creditBeforePurchase);
        }
    }

    return purchaseSuccessful;
    }

    /**
    * Builds a formatted summary of a preset signature milk tea.
    *
    * @param drinkName the name of the preset drink
    * @param machine the active special vending machine
    * @param tea the selected tea base
    * @param milk the selected milk base
    * @param sweetener the selected sweetener
    * @param sugarLevel the selected sugar level
    * @param addons the selected add-ons
    * @param iceLevel the selected ice level
    * @param size the selected cup size
    * @return a formatted preset drink summary
    */
    private String buildPresetOrderSummary(String drinkName, SpecialVendingMachine machine, int tea, int milk, int sweetener, int sugarLevel, ArrayList<Integer> addons, int iceLevel, int size) {

        StringBuilder summary = new StringBuilder();

        summary.append("========== ").append(drinkName.toUpperCase()).append(" ==========\n\n");

        summary.append("Tea Base : ").append(machine.getItemTemplates()[tea].getName()).append("\n");
        summary.append("Milk Base: ").append(machine.getItemTemplates()[milk].getName()).append("\n");

        if (sweetener == -1) {
            summary.append("Sweetener: None\n");
        } else {
            summary.append("Sweetener: ").append(machine.getItemTemplates()[sweetener].getName()).append("\n");
        }

        summary.append("Sugar Level: ").append(getSugarText(sugarLevel)).append("\n");
        summary.append("Ice Level: ").append(getIceText(iceLevel)).append("\n");
        summary.append("Size     : ").append(getSizeText(size)).append("\n");

        summary.append("Add-ons  :\n");

        for (int addon : addons) {
            summary.append("- ").append(machine.getItemTemplates()[addon].getName()).append("\n");
        }

        summary.append("==========================================");

        return summary.toString();
    }

    /**
    * Returns the display text corresponding to the selected ice level.
    *
   * @param iceLevel the selected ice level
    * @return the ice level as display text
    */
    private String getIceText(int iceLevel) {

        String iceText = "Regular Ice";

        switch (iceLevel) {
            case SpecialVendingMachine.NO_ICE:
                iceText = "No Ice";
                break;

            case SpecialVendingMachine.LESS_ICE:
                iceText = "Less Ice";
                break;

            case SpecialVendingMachine.REGULAR_ICE:
                iceText = "Regular Ice";
                break;

            case SpecialVendingMachine.EXTRA_ICE:
                iceText = "Extra Ice";
                break;
        }

        return iceText;
    }

    /**
    * Registers listeners that automatically update the available sugar
    * level options whenever the selected cup size changes.
    */
    private void addSugarSizeListeners() {
        smallButton.addActionListener(e -> sugarLevelHandler());
        mediumButton.addActionListener(e -> sugarLevelHandler());
        largeButton.addActionListener(e -> sugarLevelHandler());
    }

    /**
    * Updates the available sugar level options based on the selected
    * sweetener and cup size.
    */
    private void sugarLevelHandler() {
        String selectedSweetener = (String) sweetenerComboBox.getSelectedItem();
        int selectedSize = getSelectedSize();

        if ("None".equals(selectedSweetener)) {
            noSugarButton.setEnabled(true);
            halfSugarButton.setEnabled(false);
            fullSugarButton.setEnabled(false);

            noSugarButton.setSelected(true);

        } else if (selectedSize == SpecialVendingMachine.SMALL) {
            noSugarButton.setEnabled(false);
            halfSugarButton.setEnabled(false);
            fullSugarButton.setEnabled(true);

            fullSugarButton.setSelected(true);

        } else {
            noSugarButton.setEnabled(false);
            halfSugarButton.setEnabled(true);
            fullSugarButton.setEnabled(true);

            if (noSugarButton.isSelected()) {
                halfSugarButton.setSelected(true);
            }
        }
    }

    /**
    * Displays a specific error message explaining why a custom milk tea
    * purchase failed, including insufficient ingredients, insufficient
    * funds, or unavailable change.
    *
    * @param machine the active special vending machine
    * @param tea the selected tea base
    * @param milk the selected milk base
    * @param sweetener the selected sweetener
    * @param sugarLevel the selected sugar level
    * @param addons the selected add-ons
    * @param iceLevel the selected ice level
    * @param size the selected cup size
    * @param creditBeforePurchase the customer's available credit before
    *                             attempting the purchase
    */
    private void showMilkTeaPurchaseError(SpecialVendingMachine machine, int tea, int milk, int sweetener, int sugarLevel, ArrayList<Integer> addons, int iceLevel, int size,double creditBeforePurchase) {

        int multiplier = getSizeMultiplier(size);

        int sweetenerServings = getSweetenerServings(sugarLevel, multiplier);

        int iceServings = getIceServings(iceLevel);

        int[] requiredStock = new int[machine.getSlots().length];

        requiredStock[tea] += multiplier;
        requiredStock[milk] += multiplier;
        requiredStock[SpecialVendingMachine.ICE] += iceServings;

        if (sweetener != -1) {
            requiredStock[sweetener] += sweetenerServings;
        }

        for (int addon : addons) {
            requiredStock[addon] += multiplier;
        }

        boolean insufficientStock = false;
        String insufficientIngredient = "";
        int neededStock = 0;
        int availableStock = 0;

        for (int i = 0; i < requiredStock.length && !insufficientStock; i++) {

            int needed = requiredStock[i];

            if (needed > 0) {

                int available =machine.getSlots()[i].getCurrentInSlotItems();

                if (available < needed) {

                    insufficientStock = true;

                    insufficientIngredient =machine.getItemTemplates()[i].getName();

                    neededStock = needed;
                    availableStock = available;
                }
            }
        }

        if (insufficientStock) {

            JOptionPane.showMessageDialog(
                this,
                "The purchase could not be completed.\n\n"
                + "Not enough stock for: "
                + insufficientIngredient
                + "\nNeeded: " + neededStock
                + "\nAvailable: " + availableStock,
                "Insufficient Ingredient Stock",
                JOptionPane.ERROR_MESSAGE
        );

        } else {

            double totalPrice = machine.getItemTemplates()[tea].getPrice()* multiplier;

            totalPrice += machine.getItemTemplates()[milk].getPrice()* multiplier;

            if (sweetener != -1) {

                totalPrice +=machine.getItemTemplates()[sweetener].getPrice()* sweetenerServings;
            }

            for (int addon : addons) {

                totalPrice += machine.getItemTemplates()[addon].getPrice()* multiplier;
            }

            totalPrice += machine.getItemTemplates()[SpecialVendingMachine.ICE].getPrice()* iceServings;

            if (creditBeforePurchase < totalPrice) {

            double missingAmount = totalPrice - creditBeforePurchase;

                JOptionPane.showMessageDialog(
                    this,
                    "Insufficient funds.\n\n"
                    + "Special Milk Tea Price: PHP "
                    + String.format("%.2f", totalPrice)
                    + "\nAmount Inserted: PHP "
                    + String.format(
                            "%.2f",
                            creditBeforePurchase
                    )
                    + "\nMissing Amount: PHP "
                    + String.format(
                            "%.2f",
                            missingAmount
                    )
                    + "\n\nPlease insert more money "
                    + "or cancel your transaction.",
                    "Insufficient Funds",
                    JOptionPane.ERROR_MESSAGE);

            } else {

                double changeDue = creditBeforePurchase - totalPrice;

                JOptionPane.showMessageDialog(
                    this,
                    "The machine cannot dispense "
                    + "the required exact change.\n\n"
                    + "Change Due: PHP "
                    + String.format("%.2f", changeDue)
                    + "\n\nYour inserted money "
                    + "has been refunded.",
                    "Exact Change Unavailable",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
