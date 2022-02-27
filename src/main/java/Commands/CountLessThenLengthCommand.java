package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;

/**
 * Command, which prints the number of elements whose "length" less than the given
 * @see HashTableCommand
 * @see Command
 */
public class CountLessThenLengthCommand extends HashTableCommand {
    /**
     * Just given "length"
     */
    private int length;

    public CountLessThenLengthCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, movieHashtable);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        if (state == Executor.ExecuteState.VALIDATE) {
            return true;
        }
        int count = 0;
        for (Movie movie: getMovieHashtable().values()) {
            if (movie.getLength() < length) {
                count++;
            }
        }
        System.out.println("Found " + count + " movies with length less than " + length);
        return true;
    }

    /**
     * @see Command
     * @see BadArgumentsException
     */
    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            length = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "value must be integer");
        }
    }
}
