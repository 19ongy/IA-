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
    private MoodEntry moodEntry = new MoodEntry();
    private LocalDate localDate = LocalDate.now();
    private LocalTime localTime = LocalTime.now();
    public SetTimer Timer = new SetTimer();
    private MoodEntry.Mood moodBefore;
    private MoodEntry.Mood moodAfter;
    private int sessionLength;
    private String subject;
    private String startMonth;
    private int startDay;
    private String endMonth;
    private int endDay;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;


    //constructor
    //moodBefore, moodAfter, sessionLength, subject, startMonth, startDay, endMonth, endDay, startHour,
    //startMinute, endHour, endMinute
    public SessionManager(){
        moodBefore = MoodEntry.Mood.UNSPECIFIED;
        moodAfter = MoodEntry.Mood.UNSPECIFIED;
        sessionLength = 0;
        subject = "";
        startMonth = null;
        startDay = 0;
        endMonth = null;
        endDay = 0;
        startHour = 0;
        startMinute = 0;
        endHour = 0;
        endMinute = 0;
    }


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

        /*Session session = new Session(moodBefore, moodAfter, sessionLength, subject,
                startMonth, startDay, endMonth, endDay,
                startHour, startMinute, endHour, endMinute);

        saveSession(session);

         */
    }


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
