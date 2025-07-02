//is the starting menu for users when they choose what they want to do
//e.g. set a reminder/ start a study session/ etc etc

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);

    //welcome menu, similar to home page, allows access to all features
    public void startMenu(int answer){
        ReminderModule module = new ReminderModule();

        SessionManager sessionManager = new SessionManager();

        System.out.println("Welcome to study app! ");
        System.out.println("OPTIONS: \n1. set session \n2. set reminder \n3. Study stats \n4. Settings");

        switch(answer){
            case 1:
                //sessionManager.createSession();
            case 2:
                module.menu();
        }

    }

    public void startWMenu(){

    }
}
