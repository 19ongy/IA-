public class data {
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
    public data(String month, int day, int starthour, int startmin, int endhour, int endMinute, int moodbefore, int moodAfter, String subject){
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

    //moods: happy, sad, tired, determined, demotivated
    String[] moods = {"happy", "sad", "tired", "determined", "demotivated", "cancel"};

    //gets mood of the person before and after
    public String getMood(int num){
        if(num > 5 || num < 0){
            System.out.println("you have entered an invalid mood :( ");
        }else if(num == 5){
            return null;
        }else{
            return moods[num-1];
        }
        return null;
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

    public void sessionOverview(){
        System.out.println("-----------------");
        System.out.println("Date: " + day + " . " + month);
        System.out.println("Time begun: " + startHour + ":" + startMin);
        System.out.println("Time ended: " + endHour + ":" + endMin);
        System.out.println("Total time studied: " + timeStudied(startHour, startMin, endHour, endMin));
        System.out.println("Subject: " + subject);
        System.out.println("Mood before: " + getMood(moodBefore));
        System.out.println("Mood after: " + getMood(moodAfter));
    }


}
