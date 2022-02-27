package Commands;

import MovieObjects.JsonMovieCodec;
import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Command, which saves the collection to a file
 * @see FileCommand
 * @see Command
 */
public class SaveCommand extends FileCommand {

    public SaveCommand(String commandName, String fileName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, fileName, movieHashtable);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ReadersExecutors.Executor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(Executor.ExecuteState state) {
        if (state == Executor.ExecuteState.VALIDATE) {
            File tmp = new File("tmpFile" + Math.random() * Integer.MAX_VALUE);
            try {
                tmp.createNewFile();
                JsonMovieCodec.writeToFile(tmp.getName(), getMovieHashtable());
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                tmp.delete();
            }
        }
        else if (state == Executor.ExecuteState.EXECUTE) {
            try {
                JsonMovieCodec.writeToFile(getFileName(), getMovieHashtable());
                System.out.println("Collection has been saved");
                return true;
            } catch (IOException e) {
                System.out.println("\u001B[31m" + "ERROR: " + e.getMessage() + "\u001B[0m");
                return false;
            }
        } else {
            throw new RuntimeException("undefined execute state?");
        }
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
