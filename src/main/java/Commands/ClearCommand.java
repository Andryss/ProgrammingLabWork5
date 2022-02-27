package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;

/**
 * Command, which clears the collection
 * @see HashTableCommand
 * @see Command
 */
public class ClearCommand extends HashTableCommand {

    public ClearCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, movieHashtable);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        getMovieHashtable().clear();
        if (state == Executor.ExecuteState.EXECUTE) {
            System.out.println("Collection is clear now");
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
