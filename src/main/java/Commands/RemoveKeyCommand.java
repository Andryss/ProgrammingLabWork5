package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.Hashtable;

/**
 * Command, which deletes an element with given key\
 * @see HashTableCommand
 * @see Command
 */
public class RemoveKeyCommand extends HashTableCommand {
    /**
     * Just given key
     */
    private Integer key;

    public RemoveKeyCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, movieHashtable);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        if (getMovieHashtable().remove(key) != null) {
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Element with key \"" + key + "\" has been removed");
            }
        } else {
            if (state == Executor.ExecuteState.EXECUTE) {
                System.out.println("Nothing has been removed");
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
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }
}
