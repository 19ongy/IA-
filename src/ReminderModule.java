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

        }else if(answer == 5){

        }
    }

    public String getTime(){
        System.out.println("Enter time: (Format HHMM) ");
            return scanner.next();
    }





}
