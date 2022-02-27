package Commands;

import MovieObjects.Movie;

import java.util.Hashtable;

/**
 * Type of command, which can work with elements if Hashtable (not adding)
 * @see NameableCommand
 */
public abstract class HashTableCommand extends NameableCommand {
    /**
     * Hashtable command need to work with
     */
    private final Hashtable<Integer, Movie> movieHashtable;

    /**
     * Constructor with name and Hashtable
     * @param movieHashtable Hashtable to work with
     * @see NameableCommand
     */
    public HashTableCommand(String commandName, Hashtable<Integer, Movie> movieHashtable) {
        super(commandName);
        this.movieHashtable = movieHashtable;
    }

    public Hashtable<Integer, Movie> getMovieHashtable() {
        return movieHashtable;
    }
}
