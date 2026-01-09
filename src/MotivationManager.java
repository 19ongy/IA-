import java.util.*;
import java.time.LocalTime;
import java.util.Random;

public class MotivationManager {
    //general motivational quotes
    private static final List<String> generalQuotes = List.of(
            "Progress is progress, no matter how small.",
            "You’re doing better than you think.",
            "Every step forward counts.",
            "Life is like riding a bicycle. To keep your balance, you must keep moving. — Albert Einstein",
            "Success is the sum of small efforts, repeated day in and day out. — Robert Collier"
    );

    //medium intensity motivational quotes
    private static final List<String> mediumQuotes = List.of(
            "You’re allowed to feel off. Just don’t forget how strong you are.",
            "Even slow progress is progress.",
            "You’re allowed to pause, not to give up.",
            "It does not matter how slowly you go as long as you do not stop. – Confucius",
            "Success is the sum of small efforts, repeated day in and day out. – Robert Collier",
            "Courage doesn’t always roar. Sometimes it’s the quiet voice at the end of the day saying, ‘I will try again tomorrow.’ – Mary Anne Radmacher",
            "Start where you are. Use what you have. Do what you can. – Arthur Ashe",
            "Don’t watch the clock; do what it does. Keep going. – Sam Levenson",
            "Small deeds done are better than great deeds planned. – Peter Marshall",
            "You don’t have to be great to start, but you have to start to be great. – Zig Ziglar",
            "Hardships often prepare ordinary people for an extraordinary destiny. – C.S. Lewis",
            "Fall seven times and stand up eight. – Japanese Proverb"
    );

    //high motivation ones
    private static final List<String> intenseQuotes = List.of(
            "Do something today that your future self will thank you for. – Sean Patrick Flanery",
            "Don’t wait for opportunity. Create it. – George Bernard Shaw",
            "The only limit to our realization of tomorrow is our doubts of today. – Franklin D. Roosevelt",
            "Act as if what you do makes a difference. It does. – William James",
            "Whatever you can do, or dream you can, begin it. Boldness has genius, power, and magic in it. – Johann Wolfgang von Goethe",
            "Don’t wish it were easier. Wish you were better. – Jim Rohn",
            "Push yourself, because no one else is going to do it for you. – Unknown",
            "Success usually comes to those who are too busy to be looking for it. – Henry David Thoreau",
            "Great things never come from comfort zones. – Neil Strauss",
            "You miss 100% of the shots you don’t take. – Wayne Gretzky",
            "Strength does not come from physical capacity. It comes from an indomitable will. – Mahatma Gandhi",
            "Do not stop thinking of life as an adventure. – Eleanor Roosevelt"

    );

    //late night quotes
    private static final List<String> nightQuotes = List.of(
            "Your brain learns best when rested. Consider getting some sleep!",
            "REM sleep is important for memory and focus. Don’t stay up too late.",
            "Even a short nap can recharge your mind for more progress.",
            "Sleep is the best meditation. – Dalai Lama",
            "Rest and self-care are so important. When you take time to replenish your spirit, it allows you to serve others from the overflow. – Eleanor Brown",
            "Don’t sacrifice your sleep for temporary gains. Your body will pay the price tomorrow. – Unknown",
            "A good laugh and a long sleep are the two best cures for anything. – Irish Proverb",
            "Your body needs rest to function at its best. Ignoring it leads to mistakes, stress, and burnout. – Unknown",
            "Even superheroes need sleep. Don’t ignore the signs of exhaustion. – Unknown",
            "If you feel jittery, anxious, or your hands shake, it’s your body’s way of saying: slow down. – Unknown",
            "Sleep is not a luxury, it’s a necessity. Treat it as such. – Arianna Huffington",
            "Rest is productive. Overworking only makes you less efficient and more stressed. – Unknown",
            "When your mind races and adrenaline spikes, step back, breathe, and allow yourself to rest. – Unknown"
    );

    //returns a motivational quote based on how intense the mood they entered before was and the time of the day

    public static String getQuoteByIntensity(int intensity, boolean isNight) {
        List<String> pool;
        if(isNight){
            pool = nightQuotes;
        }
        //based on intensity
        if (intensity >= 8) {
            pool = intenseQuotes;
        } else if (intensity >= 5) {
            pool = mediumQuotes;
        } else {
            pool = generalQuotes;
        }

        return pool.get(new Random().nextInt(pool.size()));
    }
}
