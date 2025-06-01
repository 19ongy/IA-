import java.util.Objects;
import java.util.Scanner;

public class ReminderModule {
    Scanner scanner = new Scanner(System.in);
    ReminderManager manager = new ReminderManager();
    Menu menu = new Menu();

    //constructor
    public ReminderModule(){

    }

    //displays menu of all reminder operations
    public void menu(){
        System.out.println("1. Display all study reminders \n2. Add study reminder \n3. Replace reminder" +
                "\n4. Delete study reminder \n5. Delete ALL study reminders \n6. Return");
        int answer = scanner.nextInt();
        if(answer == 1){
            if(!manager.hasReminders()){
                System.out.println("there are no reminders set; please enter reminders first !");
                menu();
            }else {
                System.out.println(manager.displayReminders());
                scanner.nextLine();
                menu();
            }
        }else if(answer ==2){
            String time = getTime();
            manager.addReminder(time);
            menu();
        }else if (answer == 3) {
            if(!manager.hasReminders()){
                System.out.println("please enter reminders first !");
                menu();
            }else{
                System.out.println(manager.displayReminders());
                System.out.println("which reminder would you like to replace");
                int remToDelete = scanner.nextInt();
                String time = getTime();
                manager.replaceReminder(remToDelete, time);
                menu();
            }
        }else if(answer ==4) {
            System.out.println(manager.displayReminders());
            System.out.println("Which reminder would you like to delete? ");
            int remToDelete = scanner.nextInt();
            manager.deleteReminder(remToDelete);
            menu();

        }else if(answer == 5){
            System.out.println(manager.displayReminders());
            if(confirmAction("Are you sure you want to delete all?")){
                manager.deleteAllReminders();
                menu();
            }else{
                menu();
            }
        }else if(answer == 6){
            menu.startMenu();
        }
    }

    public String getTime(){
        System.out.println("Enter time: (Format HHMM) ");
            return scanner.next();
    }

    public boolean confirmAction(String question){
        System.out.println(question + "(format y/n)");
        String answer = scanner.next();
        if(Objects.equals(answer, "y")){
            System.out.println("verified");
            return true;
        }else if(Objects.equals(answer, "n")){
            System.out.println("unverified");
            return false;
        }else{
            System.out.println("invalid");
            return false;
        }
    }





}
