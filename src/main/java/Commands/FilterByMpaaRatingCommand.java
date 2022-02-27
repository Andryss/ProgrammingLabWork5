package Commands;

import MovieObjects.Movie;
import MovieObjects.Movie.MpaaRating;
import ReadersExecutors.Executor;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Command, which prints an elements whose "mpaaRating" is equal to the given
 * @see HashTableCommand
 * @see Command
 */
public class FilterByMpaaRatingCommand extends HashTableCommand {
    /**
     * Map for counting elements with the same "mpaaRating"
     */
    private MpaaRating mpaaRating;

    public FilterByMpaaRatingCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
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
        System.out.println("Found movies with \"" + mpaaRating + "\" mpaa rating:");
        boolean nothing = true;
        for (Integer key: getMovieHashtable().keySet()) {
            Movie movie = getMovieHashtable().get(key);
            if (movie.getMpaaRating() == mpaaRating) {
                System.out.printf("%10d - %s\n", key, getMovieHashtable().get(key));
                nothing = false;
            }
        }
        if (nothing) {
            System.out.println("*nothing*");
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
            mpaaRating = MpaaRating.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentsFormatException(getCommandName(), "value must be one of: " + Arrays.toString(MpaaRating.values()));
        }
    }
}
