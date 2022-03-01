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
     * @see ElementCommand
     */
    @Override
    protected void actionWithNewMovie(Movie movie, Executor.ExecuteState state) {
        if (movie.compareTo(getMovieHashtable().get(key)) > 0) {
            getMovieHashtable().put(key, movie);
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Element greater than the old one has been inserted");
            }
        } else {
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Nothing was happened");
            }
        }
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
