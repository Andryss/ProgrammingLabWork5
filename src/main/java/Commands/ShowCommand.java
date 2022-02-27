package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;

/**
 * Command, which prints all elements in the collection
 * @see HashTableCommand
 * @see Command
 */
public class ShowCommand extends HashTableCommand {

    public ShowCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
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
        System.out.println("Collection contains:");
        if (getMovieHashtable().size() == 0) {
            System.out.println("*nothing*");
        }
        for (Integer key : getMovieHashtable().keySet()) {
            System.out.printf("%10d - %s\n", key, getMovieHashtable().get(key));
            // System.out.println(key + " - " + getMovieHashtable().get(key));
        }
        return true;
    }

    /**
     * @see Command
     * @see BadArgumentsException
     */
    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
