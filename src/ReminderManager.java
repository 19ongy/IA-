import java.util.ArrayList;

public class ReminderManager {
    private ArrayList<ReminderTime> studyReminders = new ArrayList<>();

    public ArrayList<ReminderTime> getStudyReminders() {
        return studyReminders;
    }

    public void setStudyReminders(ArrayList<ReminderTime> studyReminders) {
        this.studyReminders = studyReminders;
    }

    public ReminderTime convertTime(String time){
        if(time.length() != 4){
            System.out.println("INVALID INPUT");
            return null;
        }else{
            int hourInput = Integer.parseInt(time.substring(0, 2));
            int minInput = Integer.parseInt(time.substring(2,4));

            //checks whether hour is above 24 and min is above 60
            if((hourInput > 24) || (minInput >= 60)){
                System.out.println("INVALID INPUT");
                return null;
            }else{
                return new ReminderTime(hourInput, minInput);
            }
        }
    }

    //displays all the reminders set with a number ( i ) next to it
    public String displayReminders() {
        if (studyReminders.size() != 0) {
            StringBuilder result = new StringBuilder("STUDY REMINDERS SET AT:\n");
            for (int i = 0; i < studyReminders.size(); i++) {
                int hour = studyReminders.get(i).getHour();
                int min = studyReminders.get(i).getMin();
                result.append("( ")
                        .append(i + 1)
                        .append(" ) ")
                        .append(String.format("%02d : %02d", hour, min))
                        .append("\n");
            }
            return result.toString();
        } else {
            return "No study reminders set yet :<";
        }
    }


    //adds the time for reminder onto the array list
    //reminder messages is optional
    public void addReminder(String time){
        studyReminders.add(convertTime(time));
        System.out.println("New reminder added ");
    }


    public void deleteReminder(int remToDelete){
        displayReminders();
        System.out.println("Which reminder would you like to delete? ");
        studyReminders.remove(remToDelete -1);
        System.out.println("deleted");
    }


    public void replaceReminder(){
        displayReminders();
        if(studyReminders.isEmpty()) {
            System.out.println("Please set a reminder first");
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

    /*
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

     */

}
