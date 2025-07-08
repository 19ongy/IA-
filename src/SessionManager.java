/*
CLASS SUMMARY:
- in charge of file managing, and storing the data from study sessions in the text files
 */

import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        this.moodBefore = null;
        this.moodAfter = null;
        this.subject = null;
        this.sessionLength = 0;
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

    //getter methods
    public String getSubject(){
        return this.subject;
    }

    public int getLength(){
        return sessionLength;
    }

    public MoodEntry.Mood getMoodBefore(){
        return moodBefore;
    }

    public MoodEntry.Mood getMoodAfter(){
        return moodAfter;
    }



    public String toFileString() {
        return(moodBefore.toString() + "," + moodAfter.toString() + "," +
                sessionLength + "," + subject + "," + startLocalDate.toString() + "," +
                startLocalTime.toString() + "," + endLocalDate.toString() + "," +
                endLocalTime.toString()
        );

    }

    public void saveSession(Session session) {
        try {
            FileWriter writer = new FileWriter("session_data.txt", true);
            writer.write(toFileString() + "\n");
            System.out.println("nice");
        } catch (IOException e) {
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

}
