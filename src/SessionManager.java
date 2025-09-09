/*
CLASS SUMMARY:
- in charge of file managing, and storing the data from study sessions in the text files
 */

import java.io.*;
import java.util.ArrayList;
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

    //experiment - this one uses LocalData.now() with a capital letter
    public String setStartDate() {
        LocalDate date = LocalDate.now();
        this.startLocalDate = date;
        return date.toString();
    }

    public String setStartTime() {
        LocalTime time = LocalTime.now();
        this.startLocalTime = time;
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    //this one uses it with a lower case
    public String setEndDate(){
        LocalDate date = LocalDate.now();
        this.endLocalDate = date;
        return String.valueOf(date);
    }

    public String setEndTime(){
        LocalTime time = LocalTime.now();
        this.endLocalTime = time;
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
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
        String formattedStartTime = String.format("%02d:%02d", startLocalTime.getHour(), startLocalTime.getMinute());
        String formattedEndTime = String.format("%02d:%02d", endLocalTime.getHour(), endLocalTime.getMinute());

        return(moodBefore.toString() + "," + moodAfter.toString() + "," +
                sessionLength + "," + subject + "," + startLocalDate.toString() + "," +
                formattedStartTime + "," + endLocalDate.toString() + "," +
                formattedEndTime
        );

    }

    public void saveSession() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("session_data.txt", true))) {
            writer.write(this.toFileString());
            writer.newLine(); // Adds a newline after each session
            System.out.println("Session saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reading the lines and splitting up the data into their respective categories
    public static ArrayList<SessionManager> readSessions(){
        ArrayList<SessionManager> sessions = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("session_data.txt"))){
            String line;
            while((line = br.readLine())!= null){
                String[] parts = line.split(",");
                //checks whether all the data is there
                if(parts.length == 8){
                    SessionManager session = new SessionManager();
                    session.setMoodBefore(parts[0]);
                    session.setMoodAfter(parts[1]);
                    session.setSessionLength(Integer.parseInt(parts[2]));
                    session.setSubject(parts[3]);
                    session.startLocalDate = LocalDate.parse(parts[4]);
                    session.startLocalTime = LocalTime.parse(parts[5]);
                    session.endLocalDate = LocalDate.parse(parts[6]);
                    session.endLocalTime = LocalTime.parse(parts[7]);
                    sessions.add(session);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sessions;
    }
}
