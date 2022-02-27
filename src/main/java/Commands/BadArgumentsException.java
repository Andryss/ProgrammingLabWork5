package Commands;

import ReadersExecutors.CommandException;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception, when command arguments are incorrect
 */
public class BadArgumentsException extends CommandException {
    /**
     * Map with examples of commands (if user always print "help me please!!!" instead of "help")
     */
    private static final Map<String, String> examples = new HashMap<>();

    static {
        examples.put("help", "help");
        examples.put("info", "info");
        examples.put("show", "show");
        examples.put("insert", "insert 5555");
        examples.put("update", "update 30");
        examples.put("remove_key", "remove_key -126");
        examples.put("clear", "clear");
        examples.put("save", "save");
        examples.put("execute_script", "execute_script someScript");
        examples.put("exit", "exit");
        examples.put("history", "history");
        examples.put("replace_if_greater", "replace_if_greater 600500");
        examples.put("remove_lower_key", "remove_lower_key -13");
        examples.put("group_counting_by_length", "group_counting_by_length");
        examples.put("count_less_than_length", "count_less_than_length 90");
        examples.put("filter_by_mpaa_rating", "filter_by_mpaa_rating G");
    }

    /**
     * Constructor without reason
     * @param command name of command
     */
    public BadArgumentsException(String command) {
        this(command, null);
    }

    /**
     * Constructor with reason
     * @param command name of command
     * @param reason reason of exception
     * @see CommandException
     */
    public BadArgumentsException(String command, String reason) {
        super(command, reason);
    }

    static Map<String, String> getExamples() {
        return examples;
    }

    @Override
    public String getMessage() {
        if (getReason() != null) {
            return "\u001B[31m" + "ERROR: bad arguments command \"" + getCommand() + "\" (" + getReason() + ")" + "\u001B[0m";
        }
        String example = examples.get(getCommand());
        if (example != null) {
            return "\u001B[31m" + "ERROR: bad arguments command \"" + getCommand() + "\" (example: \"" + examples.get(getCommand()) + "\")" + "\u001B[0m";
        } else {
            return "\u001B[31m" + "ERROR: bad arguments command \"" + getCommand() + "\" (try another variations)" + "\u001B[0m";
        }
    }
}
