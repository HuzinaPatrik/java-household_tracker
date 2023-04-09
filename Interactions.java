import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class Interactions {
    private static Scanner mScanner;
    private static Timer mTimer = new Timer();
    private static boolean mTimerFrozen = false;
    private static TimerTask mTask = new TimerTask() {

        @Override
        public void run() {
            if (mScanner.hasNextLine()) {
                String nextLine = mScanner.nextLine().toLowerCase();

                if (mTimerFrozen) {
                    return;
                }

                if (interactions.contains(nextLine)) {
                    if (nextLine.equals("l-p") || nextLine.equals("l-tp")) {
                        mTimerFrozen = true;

                        String date = Main.getTodayDate();

                        if (nextLine.equals("l-p")) {
                            System.out.println("Ohh you want me to list your plans for today. Sure here they are:");
                        } else if (nextLine.equals("l-tp")) {
                            System.out.println("Ohh you want to list your plans for tomorrow. Sure here they are:");

                            date = Main.getTomorrowDate();
                        }

                        BufferedReader bufReader = null;
                        try {
                            bufReader = new BufferedReader(new FileReader(date + ".txt"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        ArrayList<String> listOfLines = new ArrayList<>();
                        String line = null;
                        try {
                            line = bufReader.readLine();

                            while (line != null) {
                                listOfLines.add(line);
                                line = bufReader.readLine();
                            }

                            bufReader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        for (String s : listOfLines) {
                            System.out.println(s);
                        }

                        if (nextLine.equals("l-p")) {
                            System.out.println("If you wanna add to those plans use: 'A-P' in the console!");
                            System.out.println("If you wanna remove from those plans use: 'R-P' in the console!");
                            addInteraction("A-P");
                            addInteraction("R-P");
                        } else if (nextLine.equals("l-tp")) {
                            System.out.println("If you wanna add to those plans use: 'A-TP' in the console!");
                            System.out.println("If you wanna remove from those plans use: 'R-TP' in the console!");
                            addInteraction("A-TP");
                            addInteraction("R-TP");
                        }

                        mTimerFrozen = false;
                    } else if (nextLine.equals("c-tp") || nextLine.equals("c-p")) {
                        mTimerFrozen = true;

                        String date = Main.getTodayDate();

                        if (nextLine.equals("c-p")) {
                            System.out.println("You started creating the plans for today, if you want to end adding into type 'end' into the console!");
                        } else if (nextLine.equals("c-tp")) {
                            System.out.println("You started creating the plans for tomorrow, if you want to end adding into type 'end' into the console!");

                            date = Main.getTomorrowDate();
                        }

                        StringBuilder saveableText = new StringBuilder(date + " Plans:\n");

                        int id = 0;
                        while (true) {
                            if (mScanner.hasNextLine()) {
                                String text = mScanner.nextLine();

                                if (text.toLowerCase().equals("end")) {
                                    break;
                                } else {
                                    id++;
                                    saveableText.append(" - [" + id + "] " + text + "\n");
                                }
                            }
                        }

                        try {
                            FileWriter myWriter = new FileWriter(date + ".txt");
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (nextLine.equals("c-p")) {
                            System.out.println("You successfully created your plans for today. If you wanna list those plans use: 'L-P' in the console!");

                            Interactions.addInteraction("L-P");
                        } else if (nextLine.equals("c-tp")) {
                            System.out.println("You successfully created your plans for tomorrow. If you wanna list those plans use: 'L-TP' in the console!");

                            Interactions.addInteraction("L-TP");
                        }

                        mTimerFrozen = false;
                    } else if (nextLine.equals("a-tp") || nextLine.equals("a-p")) {
                        mTimerFrozen = true;

                        String date = Main.getTodayDate();

                        if (nextLine.equals("a-p")) {
                            System.out.println("You started adding more plans for today, if you want to end adding into type 'end' into the console!");
                        } else if (nextLine.equals("a-tp")) {
                            System.out.println("You started adding more plans for tomorrow, if you want to end adding into type 'end' into the console!");

                            date = Main.getTomorrowDate();
                        }

                        StringBuilder saveableText = new StringBuilder("");

                        long id = countLineBufferedReader(date + ".txt") - 1;
                        while (true) {
                            if (mScanner.hasNextLine()) {
                                String text = mScanner.nextLine();

                                if (text.toLowerCase().equals("end")) {
                                    break;
                                } else {
                                    id++;
                                    saveableText.append(" - [" + id + "] " + text + "\n");
                                }
                            }
                        }

                        try {
                            FileWriter myWriter = new FileWriter(date + ".txt", true);
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (nextLine.equals("a-p")) {
                            System.out.println("You successfully added more plans for today. If you wanna list those plans use: 'L-TP' in the console!");

                            removeInteraction("A-P");
                            removeInteraction("R-P");
                        } else if (nextLine.equals("a-tp")) {
                            System.out.println("You successfully added more plans for tomorrow. If you wanna list those plans use: 'L-TP' in the console!");

                            removeInteraction("A-TP");
                            removeInteraction("R-TP");
                        }

                        mTimerFrozen = false;
                    } else if (nextLine.equals("r-tp") || nextLine.equals("r-p")) {
                        mTimerFrozen = true;

                        String date = Main.getTodayDate();

                        if (nextLine.equals("r-tp"))
                            date = Main.getTomorrowDate();

                        System.out.println("Which lines you wanna delete? If you want to stop, use: 'end' in the console!");

                        long maxLines = countLineBufferedReader(date + ".txt") - 1;
                        List<Integer> removableLines = new ArrayList<Integer>();

                        while (true) {
                            if (mScanner.hasNextInt()) {
                                Integer line = mScanner.nextInt();

                                if (line <= maxLines) {
                                    if (!removableLines.contains(line)) {
                                        removableLines.add(line);
                                    } else {
                                        System.out.println("This line already deleted!");
                                    }
                                } else {
                                    System.out.println("This line not exists!");
                                }
                            } else if (mScanner.hasNextLine()) {
                                String text = mScanner.nextLine();

                                if (text.toLowerCase().equals("end")) {
                                    break;
                                }
                            }
                        }

                        BufferedReader bufReader = null;
                        try {
                            bufReader = new BufferedReader(new FileReader(date + ".txt"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        ArrayList<String> listOfLines = new ArrayList<>();
                        String line = null;
                        try {
                            line = bufReader.readLine();

                            while (line != null) {
                                listOfLines.add(line);
                                line = bufReader.readLine();
                            }

                            bufReader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        StringBuilder saveableText = new StringBuilder("");

                        int id = 0;
                        for (int i = 0; i < listOfLines.size(); i++) {
                            if (!removableLines.contains(i)) {
                                String text = listOfLines.get(i);

                                if (i == 0) {
                                    saveableText.append(text + "\n");
                                } else {
                                    id++;

                                    saveableText.append(" - [" + id + "] " + text.substring(text.indexOf(']') + 2, text.length()) + "\n");
                                }
                            }
                        }

                        try {
                            FileWriter myWriter = new FileWriter(date + ".txt");
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (nextLine.equals("r-p")) {
                            System.out.println("Those lines successfully deleted. If you want to list today plans use: 'L-P' in the console!");

                            removeInteraction("A-P");
                            removeInteraction("R-P");
                        } else if (nextLine.equals("r-tp")) {
                            System.out.println("Those lines successfully deleted. If you want to list tomorrow plans use: 'L-TP' in the console!");

                            removeInteraction("A-TP");
                            removeInteraction("R-TP");
                        }

                        mTimerFrozen = false;
                    } else {
                        System.out.println("This feature is still under development!");
                    }
                } else {
                    System.out.println("Sorry i can't understand what you mean with that command...");
                }
            }
        }
    };

    private static boolean status = false;
    private static boolean listenerRealStatus = false;
    private static List<String> interactions = new ArrayList<String>();

    public static void listenForInteractions(boolean newStatus) {
        status = newStatus;

        if (status) {
            /*start listening if not started already*/
            if (!listenerRealStatus) {
                listenerRealStatus = true;

                mScanner = new Scanner(System.in);
                mTimer.scheduleAtFixedRate(mTask, 0, 120);
            }
        } else {
            //stop listening if we already listen

            if (listenerRealStatus) {
                listenerRealStatus = false;

                mTimer.cancel();
                mScanner.close();
            }
        }
    }

    public static long countLineBufferedReader(String fileName) {

        long lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;

    }

    public static void addInteraction(String interaction) {
        if (!interactions.contains(interaction.toLowerCase()))
            interactions.add(interaction.toLowerCase());
    }

    public static void removeInteraction(String interaction) {
        if (interactions.contains(interaction.toLowerCase()))
            interactions.remove(interaction.toLowerCase());
    }
}
