package days;

import java.io.PrintStream;
import java.io.OutputStream;

public abstract class AocDay {
    
    protected final String input;
    protected final PrintStream out;
    
    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public AocDay(String input, PrintStream output) {
        this.input = input;

        if (output == null) {
            this.out = new PrintStream(OutputStream.nullOutputStream());
        }
        else {
            this.out = output;
        } 
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public abstract String part1();

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public abstract String part2();
}