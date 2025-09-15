import javax.swing.*;
import java.io.File;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        SessionManager manager = new SessionManager();
        GUI gui = new GUI();
        System.out.println("Working directory: " + new File(".").getAbsolutePath());

    }
}