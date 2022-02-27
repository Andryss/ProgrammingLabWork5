package ReadersExecutors;

/**
 * Exception, when this command is unexpected
 * @see CommandException
 */
public class UndefinedCommandException extends CommandException {

    public UndefinedCommandException(String command) {
        super(command);
    }

    @Override
    public String getMessage() {
        return "\u001B[31m" + "ERROR: undefined command \"" + getCommand() + "\" (type \"help\" to see list of commands)" + "\u001B[0m";
    }
}
