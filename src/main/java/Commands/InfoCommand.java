package Commands;

import MovieObjects.Movie;
import ReadersExecutors.Executor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Hashtable;

/**
 * Command, which prints short info about the collection (type, init date, length etc.)
 * @see FileCommand
 * @see Command
 */
public class InfoCommand extends FileCommand {

    public InfoCommand(String commandName, String fileName, Hashtable<Integer, Movie> movieHashtable) {
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
            return true;
        }
        File file = new File(getFileName());
        BasicFileAttributes attributes = null;
        try {
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Collection type: " + getMovieHashtable().getClass().getName() + "\n" +
                "Initialization date: " + (attributes == null ? "unknown" : attributes.creationTime().toString()) + "\n" +
                "Last modified: " + (attributes == null ? "unknown" : attributes.lastModifiedTime().toString()) + "\n" +
                "Collection length: " + getMovieHashtable().size());
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
