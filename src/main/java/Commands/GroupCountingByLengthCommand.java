package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Command, which groups the elements by the value of the "length" field, prints the number of elements in each group
 * @see HashTableCommand
 * @see Command
 */
public class GroupCountingByLengthCommand extends HashTableCommand {

    public GroupCountingByLengthCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
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
        Map<Integer, Integer> groupsByLength = new HashMap<>();
        for (Integer key : getMovieHashtable().keySet()) {
            Integer length = getMovieHashtable().get(key).getLength();
            if (!groupsByLength.containsKey(length)) {
                groupsByLength.put(length, 1);
            } else {
                groupsByLength.put(length, groupsByLength.get(length) + 1);
            }
        }
        System.out.println("*groups by length*");
        for (Integer length : groupsByLength.keySet()) {
            System.out.println(groupsByLength.get(length) + " movies with length " + length);
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
            throw new BadArgumentsCountException(getCommandName(), 0);
        }
    }
}
