package ReadersExecutors;

import MovieObjects.JsonMovieCodec;
import MovieObjects.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class, which read commands and give it to Executor
 * @see Executor
 */
public abstract class Shell {
    /**
     * Name of environment variable with filename
     */
    private static final String envName = "MovieFile";
    /**
     * Filename of json file with serialized collection
     */
    private static String envFilename;

    /**
     * Collection with an elements
     */
    protected static Hashtable<Integer, Movie> movieHashtable;
    /**
     * Scanner for reading from something (for creating Executor)
     */
    protected Scanner reader;
    /**
     * Executor, which execute all command we read
     */
    protected Executor executor;

    /**
     * Method, which set the filename of json file with collection
     * @throws SecurityException if we don't have access to environmental variable
     */
    protected static void setEnvFilename() throws SecurityException {
        envFilename = System.getenv(envName);
        if (envFilename == null) {
            throw new NullPointerException("\u001B[31m" + "ERROR: environmental variable with name \"MovieFile\" doesn't exists" + "\u001B[0m");
        }
    }

    /**
     * Method, which initialize collection
     * @throws FieldException if fields in file are incorrect
     * @throws IOException if there are some problems with file
     * @see JsonMovieCodec
     */
    protected static void loadMovieHashtable() throws FieldException, IOException {
        movieHashtable = JsonMovieCodec.readFromFile(envFilename);
    }

    /**
     * Method, which read new command from reader
     * @return read command
     */
    protected String readCommand() {
        try {
            return reader.nextLine();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("\u001B[31m" + "ERROR: incorrect input" + "\u001B[0m", e);
        }
    }

    /**
     * Method, which split string with command to get name and arguments and give it to executor
     * @param command command from user
     * @param state validating or executing
     * @throws CommandException if command have some troubles
     * @see Executor
     * @see ReadersExecutors.Executor.ExecuteState
     */
    protected void executeCommand(String command, Executor.ExecuteState state) throws CommandException {
        String[] operands = command.trim().split("\\s+", 2);
        if (operands.length == 0) {
            throw new UndefinedCommandException("");
        } else if (operands.length == 1) {
            executor.executeCommand(operands[0], new String[0], state);
        } else {
            String[] args = operands[1].split("\\s+");
            executor.executeCommand(operands[0], args, state);
        }
    }

    /**
     * Abstract method with main logic of reading and executing
     * @throws FieldException if fields of object in file are incorrect
     * @throws IOException if there are some problems with file
     * @throws CommandException if user command is incorrect
     */
    public abstract void run() throws FieldException, IOException, CommandException;

    public static String getEnvFilename() {
        return envFilename;
    }
}
