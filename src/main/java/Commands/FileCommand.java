package Commands;

import MovieObjects.Movie;

import java.util.Hashtable;

/**
 * Type of command, which can work with some file
 * @see HashTableCommand
 */
public abstract class FileCommand extends HashTableCommand {
    /**
     * Name of the file command need to work with
     */
    private final String fileName;

    /**
     * Constructor with name, Hashtable and filename
     * @param fileName name of file
     * @see HashTableCommand
     */
    public FileCommand(String commandName, String fileName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName, movieHashtable);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
