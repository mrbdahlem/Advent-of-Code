import java.nio.file.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import days.AocDay;

import java.time.LocalDate;
import java.net.*;

class Main {

    public static int YEAR = 2020;
    private static boolean RUN_TODAY_ONLY = false;
    private static boolean DISPLAY_OUTPUT = false;
    private static String SAMPLE_INPUT_FILENAME = "";

    private static double NS_TO_MS = 1/1_000_000D;

    private static PrintStream OUTSTREAM = DISPLAY_OUTPUT ? System.out : null;

    public static void main(String[] args) {
        System.out.println("\u001b[0m\033[2J\033[HAdvent of Code " + YEAR);

        int minDay = 1;
        int maxDay;

        LocalDate now = LocalDate.now();
        int day = now.getDayOfMonth();

        // If the competition is ongoing
        if (now.getYear() == YEAR && now.getMonthValue() == 12) {
            // can only go up to the lower of 25 or today's date
            maxDay = Math.min(25, day); 
        }
        else {
            // otherwise, all days are available
            maxDay = 25;
        }

        // If the competition is ongoing and working on today
        if (RUN_TODAY_ONLY && 
                now.getYear() == YEAR &&
                now.getMonthValue() == 12 &&
                now.getDayOfMonth() <= 25) {
            // only allow today's solution to run
            minDay = day;
            maxDay = day;
        }

        long totalTime = 0;

        // run the solution for all requested days
        for (day = minDay; day <= maxDay; day++) {
            for (int i = 0; i < 30; i++) {
                System.out.print("\u001b[31m*\u001b[32m*");
            }
            System.out.println("\n\u001b[0mDay " + day + ":");

            // Run the day's solution
            Result result = runDay(day, OUTSTREAM);
            // Record the responses and check that they are unique
            recordResult(day, result);

            // Display the results and timing
            System.out.print("\u001b[31mPart 1: > " + result.part1Result + " < ");
            if (!result.part1Unique) {
                System.out.print("REPEATED RESPONSE");
            }
            System.out.println();

            System.out.print("\u001b[32mPart 2: > " + result.part2Result + " < ");
            if (!result.part2Unique) {
                System.out.print("REPEATED RESPONSE");
            }
            System.out.println();

            System.out.print("\u001b[37m--- Prep:   ");
            System.out.println(String.format("%,12.2f ms", 
                                        (result.prepTime) * NS_TO_MS));
            System.out.print("\u001b[31m--- Part 1: ");
            System.out.println(String.format("%,12.2f ms", 
                                        (result.part1Time) * NS_TO_MS));
            System.out.print("\u001b[32m--- Part 2: ");
            System.out.println(String.format("%,12.2f ms", 
                                        (result.part2Time) * NS_TO_MS));

            // Add the running time of the day's solution to the total
            totalTime = totalTime + result.total();
        }

        // Display the runtime for all days that have been run
        System.out.println("\u001b[37;1mTotal runtime: " + 
                    String.format("%,12.2f ms", (totalTime) * NS_TO_MS));
    }

    private static Result runDay(int day, PrintStream output) {
        Result result = new Result();

        String input = "";

        // If a sample input file has been provided,
        // run against the sample data
        if (RUN_TODAY_ONLY && 
                SAMPLE_INPUT_FILENAME != null &&
                !SAMPLE_INPUT_FILENAME.isEmpty()) {
            try{
                input = fileContents("input/sample/" + SAMPLE_INPUT_FILENAME);
            }
            catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        else {
            // otherwise, load the day's input
            input = getInput(null, day, YEAR);
        }

        // determine the package containing the day's solution class
        String pkg = (day / 10) + "x";

        try {
            // Load the day's solution class
            String className = "days.days" + pkg + 
                                    ".day" + day + 
                                    ".Day" + day;
            Class<?> dayClass = Class.forName(className);
            Constructor<?> cnstrct = dayClass
                                .getDeclaredConstructor(String.class,
                                                        PrintStream.class);

            long startTime;
            long prepDone;
            long partDone;

            // Allow the solution to preprocess the input data
            startTime = System.nanoTime();
            AocDay solution = (AocDay)cnstrct.newInstance(input, output);
            prepDone = System.nanoTime();

            result.prepTime = prepDone - startTime;

            // Run the first part of the solution
            startTime = System.nanoTime();
            result.part1Result = solution.part1();
            partDone = System.nanoTime();

            result.part1Time = partDone - startTime;

            // Run the second part of the solution
            startTime = System.nanoTime();
            result.part2Result = solution.part2();
            partDone = System.nanoTime();

            result.part2Time = partDone - startTime;
        }
        catch (ClassNotFoundException | 
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e ) {
            System.err.println("Could not execute day " + day + ". " +
                                e.getLocalizedMessage());

            e.printStackTrace();
        }

        return result;
    }

    /**
     * A method to load everything from a file into a String
     * @param filename the name (and path) of the file to load
     * @return the contents of the file
     */
    private static String fileContents(String filename)
        throws IOException {

        File file = new File(filename);
        
        return Files.readString(file.toPath());
    }

    /**
     * Automatically download problem input if the input.txt file
     * doesn't exist
     * @param filename if specified, opens the file and reads the contents
                       into a string. If null, automatically attempts to load
                       input.txt or download if it doesn't exist.
     * @param day the day (1-25) of input to download, -1 to automatically
     *            determine
     * @param year the year (2020) of input to download, -1 to automatically
     *            determine
     * @return the the input data
     */
    private static String getInput(String filename, int day, int year) {
        boolean download = false;
        
        // If the day and/or year aren't provided, attempt to determine them
        if (day == -1 || year == -1) {
            if (LocalDate.now().getMonthValue() != 12) {
                System.err.println("Cannot automatically determine which input to download outside of December!");
                throw new RuntimeException("Cannot automatically determine which input to download outside of December!");
            }
                
            if (day == -1) {
                day = LocalDate.now().getDayOfMonth();
                if (day > 25) {
                    System.err.println("The contest has ended, I don't know what day to download.");
                    throw new RuntimeException("The contest has ended, I don't know what day to download.");
                }
            }

            if (year == -1)
                year = LocalDate.now().getYear();
        }
        
        // If there is no filename provided, use a default
        if (filename == null || filename.isEmpty()) {
            filename = String.format("input/daily/day%02d.txt", day);
            download = true;
        }

        // Attempt to load the input file if it is already downloaded
        File inputFile = new File(filename);
        if (inputFile.exists()) {
            System.out.println("Using cached input from " + filename);
            try {
                return fileContents(filename);
            }
            catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (!download) {
            System.err.println(filename + " doesn't exist.");
            throw new RuntimeException(filename + " doesn't exist.");
        }

        // make sure a session cookie has been provided for downloading
        // authorization
        String cookie = System.getenv("SESSION");
        if (cookie == null) {
            System.err.println("You need to set SESSION cookie in .env file.");  
            throw new RuntimeException("Need to set SESSION cookie in .env file."); 
        }

        
        
        String data = ""; // The downloaded file data

        try {
            System.out.println("Downloading input data for " + year + 
                                " day " + day);
            URL url = new URL("https://adventofcode.com/" + year + 
                              "/day/" + day + "/input");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie", "session=" + cookie);

            con.setInstanceFollowRedirects(true);
            int status = con.getResponseCode();

            if (status > 299) {
                System.out.println("Connection status " + status +
                     " downloading input for " + year + " day " + day);
                Reader streamReader = new InputStreamReader(con.getErrorStream());
                BufferedReader in = new BufferedReader(streamReader);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
                System.exit(0);
            }

            // Streams to handle input from http connection, output to file
            InputStream is = con.getInputStream();
            Reader streamReader = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(streamReader);
            FileOutputStream fos = new FileOutputStream(inputFile);
            PrintStream ps = new PrintStream(fos);
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                data += inputLine + "\n";
                ps.println(inputLine);
            }

            fos.close();
            is.close();
            System.out.println("Input data saved to " + filename);  
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(0);
        }

        return data;
    }

    /**
     * Record the results for a given day's parts. If a Result is provided
     * for a part of the day, it will be recorded to a file. If the file
     * exists, it will be checked to determine if the result has been tried
     * before.
     */
    private static void recordResult(int day, Result results) {
        String filebase = String.format("results/Day%02d Part%%1d.txt", day);
        
        results.part1Unique = checkAndAppend(String.format(filebase, 1), 
                                             results.part1Result);

        results.part2Unique = checkAndAppend(String.format(filebase, 2), 
                                             results.part2Result);
    }

    /**
     * Record data to a file. If the file exists, it will be checked to
     * determine if the data is already contained in it.
     * @returns true if there is no data, or if the data does not appear
     *               in the file
     */
    private static boolean checkAndAppend(String filename, String data) {
        if (data == null || data.trim().isEmpty()) {
            return true;
        }

        // Since the file will be broken down by newlines, replace any in the
        // data with carriage returns
        String dataLine = data.replace("\n", "\r");
        
        // Load the lines already recorded in the file
        String[] previous;
        try {
            previous = fileContents(filename).split("\n");
        }
        catch (IOException e) {
            previous = new String[0];
        }

        // Determine if the file contains any lines that match the data
        boolean unique = !(Arrays.stream(previous)
                            .anyMatch(s->dataLine.equals(s)));
        
        if (unique) {
            // If the data is a new response
            try {
                // add the data to the end of the file
                Files.write(
                    Paths.get(filename),
                    (dataLine + "\n").getBytes(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
            }
            catch (IOException e) {
                System.err.println("Could not append to " + filename);
                System.err.println(e);
            }
        }
        
        return unique;
    }

    private static class Result {
        public long prepTime = 0;
        public long part1Time = 0;
        public long part2Time = 0;
        public String part1Result;
        public String part2Result;
        public boolean part1Unique;
        public boolean part2Unique;

        public long total() {
            return prepTime + part1Time + part2Time;
        }
    }
}