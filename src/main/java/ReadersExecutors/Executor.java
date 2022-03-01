package ReadersExecutors;

import Commands.*;
import MovieObjects.Movie;

import java.util.*;

/**
 * Class, which executing commands
 */
public class Executor {
    /**
     * Hashtable we work with
     */
    protected final Hashtable<Integer, Movie> movieHashtable;
    /**
     * Map with command, where key is a name of command and value is a class of command
     */
    protected final Map<String, Command> commandMap = new HashMap<>();
    /**
     * Array with successful executed commands (without arguments)
     */
    private final List<String> history = new ArrayList<>();

    /**
     * Constructor with Hashtable
     * @param movieHashtable Hashtable we want work with
     */
    public Executor(Hashtable<Integer, Movie> movieHashtable) {
        this.movieHashtable = movieHashtable;
        createCommandMap();
    }

    /**
     * Method, which fill command map with correct classes
     */
    private void createCommandMap() {
        commandMap.put("help", new HelpCommand("help"));
        commandMap.put("info", new InfoCommand("info", Shell.getEnvFilename(), movieHashtable));
        commandMap.put("show", new ShowCommand("show", movieHashtable));
        commandMap.put("insert", new InsertCommand("insert", new Scanner(System.in), movieHashtable));
        commandMap.put("update", new UpdateCommand("update", new Scanner(System.in), movieHashtable));
        commandMap.put("remove_key", new RemoveKeyCommand("remove_key", movieHashtable));
        commandMap.put("clear", new ClearCommand("clear", movieHashtable));
        commandMap.put("save", new SaveCommand("save", Shell.getEnvFilename(), movieHashtable));
        commandMap.put("execute_script", new ExecuteScriptCommand("execute_script", movieHashtable, null));
        commandMap.put("exit", new ExitCommand("exit"));
        commandMap.put("history", new HistoryCommand("history", history));
        commandMap.put("replace_if_greater", new ReplaceIfGreaterCommand("replace_if_greater", new Scanner(System.in), movieHashtable));
        commandMap.put("remove_lower_key", new RemoveLowerKeyCommand("remove_lower_key", movieHashtable));
        commandMap.put("group_counting_by_length", new GroupCountingByLengthCommand("group_counting_by_length", movieHashtable));
        commandMap.put("count_less_than_length", new CountLessThenLengthCommand("count_less_than_length", movieHashtable));
        commandMap.put("filter_by_mpaa_rating", new FilterByMpaaRatingCommand("filter_by_mpaa_rating", movieHashtable));
    }

    /**
     * Method, which tries to execute command with current name, args and state
     * @param commandName name of command user tries to execute
     * @param args arguments user give
     * @param state executing state
     * @see ExecuteState
     * @throws CommandException if there are some problems with command
     */
    public void executeCommand(String commandName, String[] args, ExecuteState state) throws CommandException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new UndefinedCommandException(commandName);
        }
        command.setArgs(args);
        if (state == ExecuteState.EXECUTE) {
            System.out.println("\u001B[34m" + "START: command \"" + commandName + "\" start executing" + "\u001B[0m");
            if (command.execute(state)) {
                history.add(commandName);
                System.out.println("\u001B[32m" + "SUCCESS: command \"" + commandName + "\" successfully completed" + "\u001B[0m");
            }
        } else if (state == ExecuteState.VALIDATE) {
            try {
                if (!command.execute(state)) {
                    throw new CommandException(commandName, "can't be executed");
                }
            } catch (NoSuchElementException e) {
                throw new CommandException(commandName, e.getMessage());
            }
        } else {
            throw new RuntimeException("ExecutionState \"" + state + "\" doesn't exist");
        }
    }

    /**
     * Enum with two main states of executing command
     */
    public enum ExecuteState {
        /**
         * When command do what it should do (with prints in CMD)
         */
        EXECUTE,
        /**
         * When command is validating, for example, in script (without prints)
         */
        VALIDATE
    }
}
