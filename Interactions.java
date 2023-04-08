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
                    if (nextLine.equals("l-p")) {
                        mTimerFrozen = true;

                        System.out.println("Ohh you want me to list your plans for today. Sure here they are:");

                        String todayDate = Main.getTodayDate();

                        BufferedReader bufReader = null;
                        try {
                            bufReader = new BufferedReader(new FileReader(todayDate + ".txt"));
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

                        mTimerFrozen = false;
                    } else if (nextLine.equals("c-tp")) {
                        mTimerFrozen = true;

                        System.out.println("You started creating the plans for tomorrow, if you want to end adding into type 'end' into the console!");

                        StringBuilder saveableText = new StringBuilder(Main.getTomorrowDate() + " Plans:\n");

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
                            FileWriter myWriter = new FileWriter(Main.getTomorrowDate() + ".txt");
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("You successfully created your plans for tomorrow. If you wanna list those plans use: 'L-TP' in the console!");

                        mTimerFrozen = false;
                    } else if (nextLine.equals("l-tp")) {
                        mTimerFrozen = true;

                        System.out.println("Ohh you want to list your plans for tomorrow. Sure here they are:");

                        String tomorrowDate = Main.getTomorrowDate();

                        BufferedReader bufReader = null;
                        try {
                            bufReader = new BufferedReader(new FileReader(tomorrowDate + ".txt"));
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

                        System.out.println("If you wanna add to those plans use: 'A-TP' in the console!");
                        System.out.println("If you wanna remove from those plans use: 'R-TP' in the console!");
                        addInteraction("A-TP");
                        addInteraction("R-TP");

                        mTimerFrozen = false;
                    } else if (nextLine.equals("a-tp")) {
                        mTimerFrozen = true;

                        System.out.println("You started adding more plans for tomorrow, if you want to end adding into type 'end' into the console!");

                        StringBuilder saveableText = new StringBuilder("");

                        long id = countLineBufferedReader(Main.getTomorrowDate() + ".txt");
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
                            FileWriter myWriter = new FileWriter(Main.getTomorrowDate() + ".txt", true);
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("You successfully added more plans for tomorrow. If you wanna list those plans use: 'L-TP' in the console!");

                        removeInteraction("A-TP");
                        removeInteraction("R-TP");

                        mTimerFrozen = false;
                    } else if (nextLine.equals("r-tp")) {
                        mTimerFrozen = true;

                        System.out.println("Which lines you wanna delete? If you want to stop, use: 'end' in the console!");

                        long maxLines = countLineBufferedReader(Main.getTomorrowDate() + ".txt") - 1;
                        List<Integer> removableLines = new ArrayList<Integer>();

                        while (true) {
                            if (mScanner.hasNextInt()) {
                                Integer line = mScanner.nextInt();

                                if (!removableLines.contains(line)) {
                                    removableLines.add(line);
                                } else {
                                    System.out.println("This line already deleted!");
                                }
                            } else if (mScanner.hasNextLine()) {
                                String text = mScanner.nextLine();

                                if (text.toLowerCase().equals("end")) {
                                    break;
                                }
                            }
                        }

                        String tomorrowDate = Main.getTomorrowDate();

                        BufferedReader bufReader = null;
                        try {
                            bufReader = new BufferedReader(new FileReader(tomorrowDate + ".txt"));
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
                            if (!removableLines.contains(i + 1)) {
                                id++;
                                String text = listOfLines.get(i);

                                saveableText.append(" - [" + id + "] " + text.substring(text.indexOf(']') + 2, text.length()) + "\n");
                            }
                        }

                        try {
                            FileWriter myWriter = new FileWriter(tomorrowDate + ".txt");
                            myWriter.write(saveableText.toString());
                            myWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("Those lines successfully deleted. If you want to list tomorrow plans use: 'L-TP' in the console!");

                        removeInteraction("A-TP");
                        removeInteraction("R-TP");

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
