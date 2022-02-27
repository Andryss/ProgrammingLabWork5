package ReadersExecutors;

import Commands.*;
import MovieObjects.Movie;

import java.util.Hashtable;

/**
 * Executor, which works with files
 */
public class FileExecutor extends Executor {
    /**
     * FileShell, which call this executor
     */
    private final FileShell caller;

    /**
     * Constructor with caller and hashtable
     * @see Executor
     */
    public FileExecutor(Hashtable<Integer, Movie> movieHashtable, FileShell caller) {
        super(movieHashtable);
        this.caller = caller;
        updateCommandMap();
    }

    /**
     * Method, which replace some command in command map
     */
    private void updateCommandMap() {
        commandMap.put("insert", new InsertCommand("insert", caller.reader, movieHashtable, false));
        commandMap.put("update", new UpdateCommand("update", caller.reader, movieHashtable, false));
        commandMap.put("execute_script", new ExecuteScriptCommand("execute_script", movieHashtable, caller));
        commandMap.put("replace_if_greater", new ReplaceIfGreaterCommand("replace_if_greater", caller.reader, movieHashtable, false));
        commandMap.put("exit", new NameableCommand("exit") {
            @Override
            public boolean execute(ExecuteState state) {
                return false;
            }

            @Override
            public void setArgs(String... args) throws BadArgumentsException {
                if (args.length > 0) {
                    throw new BadArgumentsCountException(getCommandName());
                }
                throw new BadArgumentsException(getCommandName(), "do you really want \"exit\" in script? Sorry, not today");
            }
        });
    }
}
