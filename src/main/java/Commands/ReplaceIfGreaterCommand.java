package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * Command, which replaces an element by key if the new value is greater than the old one
 * @see ElementCommand
 * @see Command
 */
public class ReplaceIfGreaterCommand extends ElementCommand {
    /**
     * Just given key
     */
    private Integer key;

    public ReplaceIfGreaterCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable) {
        this(commandName, scanner, movieHashtable, true);
    }

    public ReplaceIfGreaterCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable, boolean askQuestions) {
        super(commandName, scanner, movieHashtable, askQuestions);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        Movie newMovie = readMovie(state);
        if (newMovie.compareTo(getMovieHashtable().get(key)) > 0) {
            getMovieHashtable().put(key, newMovie);
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Element greater than the old one has been inserted");
            }
        } else {
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Nothing was happened");
            }
        }
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
            Integer key = Integer.parseInt(args[0]);
            if (!getMovieHashtable().containsKey(key)) {
                throw new BadArgumentsException(getCommandName(), "movie with key \"" + key + "\" doesn't exists");
            }
            this.key = key;
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }
}
