public class Session {
    public String month;
    public int day;
    public int startHour;
    public int startMin;
    public int endHour;
    public int endMin;
    public int moodBefore;
    public int moodAfter;
    public String subject;

    //constructor
    public Session(String month, int day, int starthour, int startmin, int endhour, int endMinute, int moodbefore, int moodAfter, String subject){
        this.month = month;
        this.day = day;
        this.startHour = starthour;
        this.startMin = startmin;
        this.endHour = endhour;
        this.endMin = endMinute;
        this.moodBefore = moodbefore;
        this.moodAfter = moodAfter;
        this.subject = subject;
    }

    //setters
    public void setStartTime(int startHour, int startMin){
        this.startHour = startHour;
        this.startMin = startMin;
    }

    public void setEndTime(int endHour, int endMin){
        this.endHour = endHour;
        this.endMin = endMin;
    }
}
