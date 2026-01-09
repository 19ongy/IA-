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
    private MoodEntry.Mood moodBefore;
    private MoodEntry.Mood moodAfter;
    public LocalDate startLocalDate;
    public LocalDate endLocalDate;
    public LocalTime startLocalTime;
    public LocalTime endLocalTime;
    private String subject;
    public int sessionLength;

    //constructor for stopwatch and countdown features
    public SessionManager(){
        this.moodBefore = MoodEntry.Mood.SKIP; //sets skip to the default
        this.moodAfter = MoodEntry.Mood.SKIP;
        this.subject = null;
        this.startLocalDate = LocalDate.now();
        this.startLocalTime = LocalTime.now();
    }

    //constructor for pomodoro
    public SessionManager(String subject){
        this(); //calls the default constructor first ¬ sets everything as null or skip
        //makes having several pomodoro sessions easier
        this.subject = subject; // assigns subject specifically
    }

    //setter methods
    public void setMoodBefore(String mood){
        try{
            this.moodBefore = MoodEntry.Mood.valueOf(mood.toUpperCase());
        }catch(Exception e){
            this.moodBefore = MoodEntry.Mood.SKIP;
        }
    }

    public void setMoodAfter(String mood){
        try {
            this.moodAfter = MoodEntry.Mood.valueOf(mood.toUpperCase());
        } catch (Exception e){
            this.moodAfter = MoodEntry.Mood.SKIP;
        }
    }

    public void setStartDate() {
        LocalDate date = LocalDate.now();
        this.startLocalDate = date;
    }

    public void setStartTime() {
        LocalTime time = LocalTime.now();
        this.startLocalTime = time;
    }

    public void setEndDate(){
        LocalDate date = LocalDate.now();
        this.endLocalDate = date;
    }

    public void setEndTime(){
        LocalTime time = LocalTime.now();
        this.endLocalTime = time;
    }

    public void setSessionLength(int length){
        this.sessionLength = length;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    //getters
    public String getSubject(){
        return this.subject;
    }

    public MoodEntry.Mood getMoodBefore(){
        return moodBefore;
    }

    public MoodEntry.Mood getMoodAfter(){
        return moodAfter;
    }

    //checking to see whether its ready to save
    public boolean isComplete(){
        return startLocalDate != null && startLocalTime != null && endLocalDate != null
                && endLocalTime != null && subject != null;
    }

    //converting it into the csv data file
    public String toFileString() {
        String formattedStartTime = String.format("%02d:%02d", startLocalTime.getHour(), startLocalTime.getMinute());
        String formattedEndTime = String.format("%02d:%02d", endLocalTime.getHour(), endLocalTime.getMinute());

        return(moodBefore + "," + moodAfter + "," +
                sessionLength + "," + subject + "," + startLocalDate + "," +
                formattedStartTime + "," + endLocalDate + "," +
                formattedEndTime
        );
    }

    //saving the session
    public void saveSession() {
        if(!isComplete()){ //makes sure all the components are there before saving
            System.out.println("session incomplete, not saved");
            return;
        }

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

    //getting the sessions by date

    public static ArrayList<SessionManager> getSessionsByDate(ArrayList<SessionManager> allSessions, LocalDate date){
        ArrayList<SessionManager> result = new ArrayList<>();
        for(SessionManager session : allSessions){
            if(session.startLocalDate.equals(date)){
                result.add(session);
            }
        }
        return result;
    }

}
