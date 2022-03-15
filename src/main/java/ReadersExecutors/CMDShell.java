package ReadersExecutors;

import MovieObjects.JsonMovieCodec;
import MovieObjects.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Singleton Shell, which reading command from CMD
 * @see Shell
 */
public class CMDShell extends Shell {
    /**
     * Only one instance of CMDShell
     */
    private static final CMDShell instance = new CMDShell();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("\u001B[36m" + "THANK YOU for choosing our app to work with collections.\n" +
                "Developers are searching for the best realizations. Have a nice day :)" + "\u001B[0m")));
    }

    /**
     * Private constructor
     */
    private CMDShell() {}

    /**
     * Method which returns only one instance of shell
     * @return instance
     * @throws SecurityException if we don't have access to environmental variable
     */
    public static CMDShell getInstance() throws SecurityException {
        setEnvFilename();
        return instance;
    }

    /**
     * Initialization method, which tries to init reader, executor and hashtable
     * @throws FieldException if fields in file are incorrect
     * @throws IOException if there are some problems with file
     */
    private void initialization() throws FieldException, IOException {
        reader = new Scanner(System.in);
        try {
            loadMovieHashtable();
        } catch (FileNotFoundException | NoSuchElementException e) {
            movieHashtable = new Hashtable<>();
            JsonMovieCodec.writeToFile(getEnvFilename(), movieHashtable);
        }
        executor = new Executor(movieHashtable);
        System.out.println("\u001B[36m" + "Hi! This is a simple terminal program for working with collection.\n" +
                "I'm waiting for your commands (type \"help\" for list of available commands)." + "\u001B[0m");
    }

    /**
     * Only one public method, which starts shell
     * @throws FieldException if fields in file are incorrect
     * @throws IOException if there are some problems with file
     */
    @Override
    public void run() throws FieldException, IOException {
        initialization();
        while (true) {
            try {
                String command = readCommand();
                try {
                    executeCommand(command, Executor.ExecuteState.EXECUTE);
                } catch (CommandException e) {
                    System.err.println(e.getMessage());
                }
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }
}
