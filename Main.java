import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        String username = System.getProperty("user.name");
        System.out.println("Hi, " + username + "!");

        String todayDate = getTodayDate();
        System.out.println("Today's date is: " + todayDate);

        if (Files.exists(Path.of(todayDate + ".txt"))) {
            System.out.println("If you wanna list what's planned for today you should type 'L-P' into the console!");

            Interactions.addInteraction("L-P");
        } else {
            System.out.println("For this day you have nothing planned. If you wanna create the plan for today you should use: 'C-P' in the console"); //If he have something planned then the text should be press blablabla to list what you planed for today.

            Interactions.addInteraction("C-P");
        }

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