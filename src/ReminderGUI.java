import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ReminderGUI extends JFrame{
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel waterRem;

    private Color darkGreen = new Color(27, 77, 62);
    private Color lightGreen = new Color(200, 200, 200);
    private Color borderGreen = new Color(15, 50, 40);
    private Color backgroundGrey = new Color(46, 46, 46);
    private Color greenBlue = new Color(45, 84, 83);

    public ReminderGUI() {
        GraphicsDevice gd = MouseInfo.getPointerInfo().getDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        setTitle("Reminders ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // quit the app when we close the window
        System.out.println(width);
        System.out.println(height);
        setLocation(width-1000, height-600);

        setSize(400, 200);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        sendWaterRem();

        setVisible(true);
        cardLayout.show(cardPanel,"waterRem");
    }

    public void sendWaterRem(){
        waterRem = new JPanel(null);


        JLabel labelOutput = new JLabel("Make sure you drink water !");
        labelOutput.setBounds(0, 0, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(new Color(200, 200, 200));

        ImageIcon menuPic = new ImageIcon("new water.png");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(50, 50, menuPic.getIconWidth(), menuPic.getIconHeight());
        System.out.println("Image width: " + menuPic.getIconWidth());
        //Image newImage = imageLabel.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        //sets the background of the window to like grey
        waterRem.setBackground(greenBlue);

        waterRem.add(imageLabel);
        waterRem.add(labelOutput);
        cardPanel.add(waterRem, "waterRem");

        System.out.println("SEQUENCE: GUI_test created");
    }

    public void sendMotivRem(int intensity){

    }
}
