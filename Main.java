import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        /*
        Feature list:
            - Daily tracking of what you did / expense (You can select type and based on that)
            - Plan for the next day
            - Compare the day with the plan
            - Automatic save

            !! FUTURE IDEA for the compare feature: AI comparing and suggesting that if you add a next task it occured on the plan or not. And the AI learning by asking if it's correct prediction or not.

            - Command which can list you your old saved days.
            - History where you can list what you did on a specific day (There is an optional feature to also list the plan)
        */;

        String username = System.getProperty("user.name");
        System.out.println("Hi, " + username + "!");

        String todayDate = getTodayDate();
        System.out.println("Today's date is: " + todayDate);

        if (Files.exists(Path.of(todayDate + ".txt"))) {
            System.out.println("If you wanna list what's planned for today you should type 'L-P' into the console!");

            Interactions.addInteraction("L-P");
        } else {
            System.out.println("For this day you have nothing planned."); //If he have something planned then the text should be press blablabla to list what you planed for today.
        }

        /*!!!UGYANEZT KAPJA MEG a TODAY PLANS IS MINT A TOMORROW PLANS!!!*/

        String tomorrowDate = getTomorrowDate();

        if (Files.exists(Path.of(tomorrowDate + ".txt"))) {
            System.out.println("You already planned everything for tomorrow. If you wanna list that use: 'L-TP' in the console!"); //Add / Remove line.

            Interactions.addInteraction("L-TP");
        } else {
            System.out.println("For tomorrow you have nothing planned. If you wanna create the plan for tomorrow you should use: 'C-TP' in the console");

            Interactions.addInteraction("C-TP");
        }

        Interactions.listenForInteractions(true);
    }

    public static String getTodayDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    };

    public static String getTomorrowDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        now = now.plusDays(1);

        return dtf.format(now);
    };
}