import java.util.Scanner;

public class ReminderModule {
    Scanner scanner = new Scanner(System.in);
    ReminderManager manager = new ReminderManager();

    //constructor
    public ReminderModule(){

    }

    //displays menu of all reminder operations
    public void menu(){
        System.out.println("1. Display all study reminders \n2. Add study reminder \n3. Replace reminder" +
                "\n4. Delete study reminder \n5. Delete ALL study reminders \n6. Return menu \n7. Exit");
        int answer = scanner.nextInt();
        if(answer == 1){
            System.out.println(manager.displayReminders());
            scanner.nextLine();
            menu();
        }else if(answer ==2){
            String time = getTime();
            manager.addReminder(time);
            menu();
        }else if(answer ==4) {
            manager.displayReminders();
            System.out.println("Which reminder would you like to delete? ");
            int remToDelete = scanner.nextInt();
            manager.deleteReminder(remToDelete);

        }else if( answer == 3) {
            //replaceReminder();
        }else if(answer == 7){
            //will direct to home page ish / menu type thing in the future
        }
    }

    public String getTime(){
        System.out.println("Enter time: (Format HHMM) ");
        return scanner.next();
    }



}
