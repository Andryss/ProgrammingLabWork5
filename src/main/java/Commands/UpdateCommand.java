package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * Command, which updates an element with given id
 * @see ElementCommand
 * @see Command
 */
public class UpdateCommand extends ElementCommand {
    /**
     * Just given key
     */
    private Integer key;

    public UpdateCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable) {
        this(commandName, scanner, movieHashtable, true);
    }

    public UpdateCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable, boolean asqQuestions) {
        super(commandName, scanner, movieHashtable, asqQuestions);
    }

    /**
     * @see ElementCommand
     */
    @Override
    protected void actionWithNewMovie(Movie movie, Executor.ExecuteState state) {
        getMovieHashtable().put(key, movie);
        if (state == Executor.ExecuteState.EXECUTE) {
            System.out.println("The movie has been updated");
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
            throw new BadArgumentsFormatException(getCommandName(), "value must be integer");
        }
    }
}
