package Commands;

import MovieObjects.Movie;
import ReadersExecutors.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Command, which reads and executes script from file
 * @see HashTableCommand
 * @see Command
 * @see Executor
 * @see ReadersExecutors.Executor.ExecuteState
 */
public class ExecuteScriptCommand extends HashTableCommand {
    /**
     * Caller of this command
     */
    private final FileShell caller;
    /**
     * Just given file with script
     */
    private File file;

    public ExecuteScriptCommand(String commandName, Hashtable<Integer, Movie> movieHashtable, FileShell caller) {
        super(commandName, movieHashtable);
        this.caller = caller;
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        FileShell fileShell = new FileShell(file.getName(), caller);
        try {
            if (caller == null) {
                fileShell.validate((Hashtable<Integer, Movie>) getMovieHashtable().clone());
                System.out.println("\u001B[32m" + "Script validated successfully" + "\u001B[0m");
            } else if (state == Executor.ExecuteState.VALIDATE) {
                fileShell.validate(getMovieHashtable());
            }
            if (state == Executor.ExecuteState.EXECUTE) {
                fileShell.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (CommandException e) {
            System.out.println(e.getMessage());
            return false;
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
        file = new File(args[0]);
        if (!file.exists() || !file.isFile()) {
            throw new BadArgumentsException(getCommandName(), "script with name \"" + args[0] + "\" doesn't exists");
        }
        for (FileShell curCaller = caller; curCaller != null; curCaller = curCaller.getCaller()) {
            if (curCaller.getFileName().equals(args[0])) {
                throw new BadArgumentsException(getCommandName(), "recursion is not supported");
            }
        }
    }
}
