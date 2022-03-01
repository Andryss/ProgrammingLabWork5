package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import javax.xml.bind.ValidationEvent;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Command, which adds new element with given key
 * @see ElementCommand
 * @see Command
 */
public class InsertCommand extends ElementCommand {
    /**
     * Just given key
     */
    private Integer key;

    public InsertCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable) {
        this(commandName, scanner, movieHashtable, true);
    }

    public InsertCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable, boolean askQuestions) {
        super(commandName, scanner, movieHashtable, askQuestions);
    }

    /**
     * @see ElementCommand
     */
    @Override
    protected void actionWithNewMovie(Movie movie, Executor.ExecuteState state) {
        getMovieHashtable().put(key, movie);
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
            if (getMovieHashtable().containsKey(key)) {
                throw new BadArgumentsException(getCommandName(), "movie with key \"" + key + "\" already exists");
            }
            this.key = key;
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "value must be integer");
        }
    }
}
