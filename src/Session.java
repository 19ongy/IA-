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

    public int timeStudied(int startHour, int startMin, int endHour, int endMin){
        int hoursStudied = 0;
        int minStudied = 0;

        //have to enter it in 24 hour format
        //20:00 - 04:00
        //28:00 - 20:00 = 8 hours
        //27:00 - 20:00 = 7 hours

        if(endHour == startHour){
            hoursStudied = 0;
        }else if(endHour > startHour){
            hoursStudied = endHour - startHour;
        }else if(endHour < startHour){
            endHour = endHour + 24;
            hoursStudied = endHour - startHour;
        }

        if(endMin == startMin){
            minStudied = 0;
        }else if(endMin > startMin){
            minStudied = endMin - startMin;
        }else if(endMin < startMin){
            minStudied = (endMin + 60) - startMin;
        }

        return((hoursStudied*60) + minStudied);
    }



}
