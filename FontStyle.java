import java.awt.Font;

/**
 * Represents a font style configuration for the vending machine GUI.
 * Provides predefined font styles for titles, buttons, normal text,
 * and smaller text to maintain a consistent visual theme.
 */
public class FontStyle {

    /**
     * The font style used for titles within the vending machine interface.
     */
    public static final Font TITLE = new Font("Century Gothic", Font.BOLD, 32);

    /**
     * The font style used for buttons within the vending machine interface.
     */
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 18);

    /**
     * The font style used for normal text within the vending machine interface.
     */
    public static final Font NORMAL = new Font("Trebuchet MS", Font.PLAIN, 15);

    /**
     * The font style used for smaller text within the vending machine interface.
     */
    public static final Font SMALL = new Font("Trebuchet MS", Font.PLAIN, 13);
}