/*
CLASS SUMMARY:
- in charge of file managing, and storing the data from study sessions in the text files
 */

import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;


public class SessionManager {
    private Scanner scanner = new Scanner(System.in);
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.now();
    private MoodEntry.Mood moodBefore;
    private MoodEntry.Mood moodAfter;
    //private String moodBefore;
    //private String moodAfter;
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;
    private LocalTime startLocalTime;
    private LocalTime endLocalTime;
    private String subject;
    private int sessionLength;

    //constructor
    public SessionManager(){


    }

    //setter methods
    public String setMoodBefore(String mood){
        this.moodBefore = MoodEntry.Mood.valueOf(mood);
        return mood;
    }

    public String setMoodAfter(String mood){
        this.moodAfter = MoodEntry.Mood.valueOf(mood);
        return mood;
    }

    public String setStartDate(){
        LocalDate date = localDate.now();
        this.startLocalDate = date;
        return String.valueOf(date);
    }

    public String setEndDate(){
        LocalDate date = localDate.now();
        this.endLocalDate = date;
        return String.valueOf(date);
    }

    public String setStartTime(){
        LocalTime time = localTime.now();
        this.startLocalTime = time;
        return String.valueOf(time);
    }

    public String setEndTime(){
        LocalTime time = localTime.now();
        this.endLocalTime = time;
        return String.valueOf(time);
    }

    public String setSubject(String subject){
        this.subject = subject;
        return subject;
    }

    public String setSessionLength(int length){
        this.sessionLength = length;
        return String.valueOf(length);
    }





    public String toFileString() {
        return(moodBefore.toString() + "," + moodAfter.toString() + "," +
                sessionLength + "," + subject + "," + startLocalDate.toString() + "," +
                startLocalTime.toString() + "," + endLocalDate.toString() + "," +
                endLocalTime.toString()
        );

    }



    /*
    public void createSession(){
        System.out.println("NEW SESSION: ");

        //gets date before
        startMonth = String.valueOf(localDate.getMonth());
        startDay = localDate.getDayOfMonth();

        //time before session
        startHour = localTime.getHour();
        startMinute = localTime.getMinute();

        //gets mood from before the session
        moodBefore = moodEntry.getMood();

        //gets subject

        System.out.println("Subject being studied: ");
        subject = scanner.next();


        //sets timer
        Timer.setType();

        //duration is already set in timer class
        sessionLength = Timer.getSetTimerDuration();

        try {
            //waits until 1 second after the timer is finished before it asks the mood afterwards
            Thread.sleep(1000*(sessionLength+1));  // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //mood after
        moodAfter = moodEntry.getMood();

        //date after ( if they started studying at night and ended in the morning )
        endMonth = String.valueOf(localDate.getMonth());
        endDay = localDate.getDayOfMonth();

        //time after
        endHour = localTime.getHour();
        endMinute = localTime.getMinute();

        Session session = new Session(moodBefore, moodAfter, sessionLength, subject,
                startMonth, startDay, endMonth, endDay,
                startHour, startMinute, endHour, endMinute);

        saveSession(session);

         */


    /*attempts at reading and writing to the data file
    public void saveSession(Session session){
        try{
            FileWriter writer = new FileWriter("session_data.txt", true);
            writer.write(session.toFileString() + "\n");
            System.out.println("nice");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void readSessions() {
        try {
            FileReader fr = new FileReader("session_data.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                System.out.println("hello");
                System.out.println(line);
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

     */

}
