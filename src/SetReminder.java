import java.util.ArrayList;
import java.util.Scanner;

public class SetReminder {
    private Scanner scanner = new Scanner(System.in);
    private int numReminders;
    private ArrayList<ReminderTime> studyReminders = new ArrayList<>();

    //constructor
    public SetReminder(){
        numReminders = 0;
    }

    //delete all study reminders
    public void resetStudyRems(){
        studyReminders.clear();
    }

    //menu for study reminders
    public void menu(){
        System.out.println("1. Display all study reminders \n2. Add study reminder \n3. Replace reminder" +
                "\n4. Delete study reminder \n5. Delete ALL study reminders \n6. Return menu \n7. Exit");
        int answer = scanner.nextInt();
        if(answer == 1){
            displayReminders();
            scanner.nextLine();
            menu();
        }else if(answer ==2){
            addReminder("What time would you like to set the study reminder at?");
        }else if(answer ==4) {
            deleteReminder();
        }else if( answer == 3) {
            replaceReminder();
        }else if(answer == 7){
            //will direct to home page ish / menu type thing in the future
        }
    }

    //displays all the reminders set with a number ( i ) next to it
    public void displayReminders(){
        if(studyReminders.size() != 0) {
            System.out.println("STUDY REMINDERS SET AT: ");
            for (int i = 0; i < studyReminders.size(); i++) {
                int hour = studyReminders.get(i).getHour();
                int min = studyReminders.get(i).getMin();
                System.out.println("( " + (i + 1) + " ) " + hour + " : " + min);
            }
        }else{
            System.out.println("No study reminders set yet ! ");
        }
    }

    //adds the time for reminder onto the array list
    //reminder messages is optional
    public void addReminder(String question){
        System.out.println(question + "FORMAT HHMM");
        String time = scanner.next();
        String reminderMessage = scanner.next();
        //makes sure its HHMM
        if(time.length() != 4){
            System.out.println("INVALID INPUT");
        }else{
            int hourInput = Integer.parseInt(time.substring(0, 2));
            int minInput = Integer.parseInt(time.substring(2,4));

            //checks whether hour is above 24 and min is above 60
            if((hourInput > 24) || (minInput >= 60)){
                System.out.println("INVALID INPUT");
                menu();
            }else{
                System.out.println("Message: ");

                studyReminders.add(new ReminderTime(hourInput, minInput));
                menu();
            }
        }
    }


    public void deleteReminder(){
        displayReminders();
        System.out.println("Which reminder would you like to delete? ");
        int remToDelete = scanner.nextInt();
        studyReminders.remove(remToDelete -1);
        System.out.println("deleted");
        scanner.nextLine();
        menu();
    }

    //removes the reminder selected by user
    //uses previous addReminder method to replace the old reminder
    public void replaceReminder(){
        displayReminders();
        if(studyReminders.isEmpty()) {
            System.out.println("Please set a reminder first");
            menu();
        }else{
            System.out.println("Which reminder do you want to replace");
            int remToReplace = scanner.nextInt();
            if(remToReplace >= 1 && remToReplace <= studyReminders.size()){
                studyReminders.remove(remToReplace -1);
                //uses previous addReminder method
                addReminder("What time do you want to replace it with?");
            }else{
                System.out.println("You have entered an invalid number.");
                menu();
            }
        }
    }

    //deletes all reminders set previously
    //sends confirmation message asking whether they intended to delete all messages
    public void deleteAllReminders(){
        displayReminders();
        System.out.println("Are you sure you want to delete all reminders? (y/n)");
        String confirmation = scanner.next();
        if(confirmation.equals("y")){
            for(int i = 0; i < studyReminders.size(); i++){
                //uses previous delete method
                deleteReminder();
            }
        }else if(confirmation.equals("n")){
            menu();
        }else{
            System.out.println("invalid");
            menu();
        }
    }

}
