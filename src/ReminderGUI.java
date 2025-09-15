import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

//gui class for displaying different types of reminders
//e.g. water, study, motivation
public class ReminderGUI extends JFrame{
    //lets u switch between different reminder panels
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel waterRem;
    private JPanel studyRem;

    //custom colours
    private Color darkGreen = new Color(27, 77, 62);
    private Color lightGreen = new Color(200, 200, 200);
    private Color borderGreen = new Color(15, 50, 40);
    private Color backgroundGrey = new Color(46, 46, 46);
    private Color greenBlue = new Color(45, 84, 83);

    public ReminderGUI(String mode, String message) {
        //dynamically positions the window near bottom right of screen
        GraphicsDevice gd = MouseInfo.getPointerInfo().getDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        setTitle("Reminders ");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  // quit the app when we close the window
        setLocation(width-1050, height-630);
        setSize(400, 200);

        //setup card layout to switch between reminder types
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        sendWaterRem();
        sendStudyRem(message);

        //panel is based on mode
        if(mode.equals("study")){
            cardLayout.show(cardPanel, "studyRem");
        }else if(mode.equals("waterRem")){
            cardLayout.show(cardPanel,"waterRem");
        }
        setVisible(true);
    }

    public void sendWaterRem(){
        if(waterRem != null){
            return;
        }

        waterRem = new JPanel(null);

        //reminder message label
        JLabel labelOutput = new JLabel("Make sure you drink water !");
        labelOutput.setBounds(0, 0, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(new Color(200, 200, 200));

        //load and display label

        ImageIcon menuPic = new ImageIcon("new water.png");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(50, 50, menuPic.getIconWidth(), menuPic.getIconHeight());
        System.out.println("Image width: " + menuPic.getIconWidth());

        //sets the background of the window to like grey
        waterRem.setBackground(greenBlue);
        waterRem.add(imageLabel);
        waterRem.add(labelOutput);
        cardPanel.add(waterRem, "waterRem");

        //System.out.println("SEQUENCE: GUI_test created");
    }

    //create study reminder panel with custom message
    public void sendStudyRem(String message){
        if(studyRem != null){
            return;
        }

        studyRem = new JPanel(null);

        //user already set message, is diplayed
        JLabel labelOutput = new JLabel(message);
        labelOutput.setForeground(lightGreen);
        labelOutput.setBounds(0, 0, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));

        //loads the image drawn by winter
        ImageIcon menuPic = new ImageIcon("winter drawing smaller.png");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(50, 50, menuPic.getIconWidth(), menuPic.getIconHeight());

        studyRem.add(labelOutput);
        studyRem.add(imageLabel);
        cardPanel.add(studyRem, "studyRem");
    }

    //creates motivational reminder panel based on intesntiy scale
    public void sendMotivRem(int intensity){
        JPanel motivRem = new JPanel(null);

        //gets quote based on user mood
        String quote = MotivationManager.getQuoteByIntensity(intensity);
        //display with html formatting
        JLabel quoteLabel = new JLabel("<html><div style='text-align: center;'>" + quote + "</div></html>", SwingConstants.CENTER);
        quoteLabel.setBounds(20, 20, 360, 60);
        quoteLabel.setFont(new Font("Arial", Font.BOLD, 18));
        quoteLabel.setForeground(lightGreen);

        motivRem.setBackground(darkGreen);
        motivRem.add(quoteLabel);
        cardPanel.add(motivRem, "motivRem");

        cardLayout.show(cardPanel, "motivRem");
        setVisible(true);

    }
}
