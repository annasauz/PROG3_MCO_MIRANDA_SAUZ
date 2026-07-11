/**
 * Entry point / main class
 */

public class Driver {
    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        VendingMachineSoftwareLoop SoftwareLoop = new VendingMachineSoftwareLoop(textInterface);
        SoftwareLoop.run();
    }
}