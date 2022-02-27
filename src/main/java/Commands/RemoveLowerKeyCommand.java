package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;

/**
 * Command, which removes all elements whose key is less than given
 * @see HashTableCommand
 * @see Command
 */
public class RemoveLowerKeyCommand extends HashTableCommand {
    /**
     * Just given key
     */
    private Integer key;

    public RemoveLowerKeyCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, movieHashtable);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        for (Integer key : getMovieHashtable().keySet()) {
            if (key < this.key) {
                getMovieHashtable().remove(key);
            }
        }
        if (state == Executor.ExecuteState.EXECUTE) {
            System.out.println("All elements with key lower than \"" + key + "\" has been removed");
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
            this.key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }
}
