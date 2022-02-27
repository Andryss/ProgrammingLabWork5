package ReadersExecutors;

import MovieObjects.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Shell, which works with file (especially for executing scripts)
 */
public class FileShell extends Shell {
    /**
     * Just filename
     */
    private final String fileName;
    /**
     * Who create this FileShell (for recursion)
     */
    private final FileShell caller;
    /**
     * Number of executing command for informative mistakes
     */
    private int commandNumber = 0;

    /**
     * Constructor with filename and caller
     * @param fileName filename
     * @param caller who create this FileShell
     */
    public FileShell(String fileName, FileShell caller) {
        this.fileName = fileName;
        this.caller = caller;
    }

    /**
     * Read command with increment command number
     */
    @Override
    protected String readCommand() {
        commandNumber++;
        return super.readCommand();
    }

    /**
     * Method, which initialize reader and executor
     * @throws FileNotFoundException if file doesn't exist
     * @see Shell
     */
    private void initReaderExecutor(Hashtable<Integer, Movie> initHashtable) throws FileNotFoundException {
        reader = new Scanner(new File(fileName));
        executor = new FileExecutor(initHashtable, this);
    }

    /**
     * Method, which starts shell executing file
     * @throws IOException if there is some problems with file
     * @throws CommandException if command have some troubles
     */
    @Override
    public void run() throws IOException, CommandException {
        initReaderExecutor(movieHashtable);
        while (reader.hasNext()) {
            String command = readCommand();
            try {
                executeCommand(command, Executor.ExecuteState.EXECUTE);
            } catch (CommandException e) {
                throw new CommandException(e.getCommand(), "file \"" + fileName + "\" command " + commandNumber + " \"" + command + "\" - " +
                        e.getMessage().substring(5, e.getMessage().length() - 4));
            }
        }
    }

    /**
     * Method, which starts shell validating file
     * @param hashtable hashtable we validating
     * @throws IOException if there is some problems with file
     * @throws CommandException if command have some troubles
     */
    public void validate(Hashtable<Integer, Movie> hashtable) throws IOException, CommandException {
        initReaderExecutor(hashtable);
        while (reader.hasNext()) {
            String command = readCommand();
            try {
                executeCommand(command, Executor.ExecuteState.VALIDATE);
            } catch (CommandException e) {
                throw new CommandException(e.getCommand(), "file \"" + fileName + "\" command " + commandNumber + " \"" + command + "\" - " +
                        e.getMessage().substring(5, e.getMessage().length() - 4));
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public FileShell getCaller() {
        return caller;
    }
}
