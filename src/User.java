import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private Scanner scanner = new Scanner(System.in);
    private int numReminders;
    private ArrayList<ReminderTime> studyRems = new ArrayList<>();

    //constructor
    public User(){
        numReminders = 0;
    }

    //delete all study reminders
    public void resetStudyRems(){
        studyRems.clear();
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
            addReminder();
        }else if(answer ==4) {
            deleteReminder();
        }else if( answer == 3) {

        }else if(answer == 7){
            //will direct to home page ish / menu type thing in the future
        }
    }

    //displays all the reminders set with a number ( i ) next to it
    public void displayReminders(){
        if(studyRems.size() != 0) {
            System.out.println("STUDY REMINDERS SET AT: ");
            for (int i = 0; i < studyRems.size(); i++) {
                int hour = studyRems.get(i).getHour();
                int min = studyRems.get(i).getMin();
                System.out.println("( " + (i + 1) + " ) " + hour + " : " + min);
            }
        }else{
            System.out.println("No study reminders set yet ! ");
        }
    }

    //adds the time for reminder onto the array list
    public void addReminder(){
        System.out.println("What time do you want to set this reminder at? (FORMAT HHMM) ");
        String time = scanner.next();
        //makes sure its HHMM
        if(time.length() != 4){
            System.out.println("INVALID INPUT");
        }else{
            int hourInput = Integer.parseInt(time.substring(0, 2));
            int minInput = Integer.parseInt(time.substring(2,4));

            //checks whether hour is above 24 and min is above 60
            if((hourInput > 24) || (minInput >= 60)){
                System.out.println("INVALID INPUT");
            }else{
                studyRems.add(new ReminderTime(hourInput, minInput));
                menu();
            }
        }
    }


    public void deleteReminder(){
        displayReminders();
        System.out.println("Which reminder would you like to delete? ");
        int remToDelete = scanner.nextInt();
        studyRems.remove(remToDelete -1);
        System.out.println("deleted");
        scanner.nextLine();
        menu();
    }


}
