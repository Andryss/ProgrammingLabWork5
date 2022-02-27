package Commands;

import ReadersExecutors.Executor;

/**
 * Command, which prints a list of available commands
 * @see NameableCommand
 * @see Command
 */
public class HelpCommand extends NameableCommand {

    public HelpCommand(String commandName) {
        super(commandName);
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
        System.out.println("*List of available commands*\n" +
                "help : print a list of available commands\n" +
                "info : print short info about the collection (type, init date, length etc)\n" +
                "show : print all elements in the collection\n" +
                "insert null {element} : add new element with given key\n" +
                "update null {element} : update an element with given key\n" +
                "remove_key null : delete an element with given key\n" +
                "clear : clear the collection\n" +
                "save : save the collection to a file\n" +
                "execute_script file_name : read and execute script from file\n" +
                "exit : end the program (without saving)\n" +
                "history : print last 13 commands (without arguments)\n" +
                "replace_if_greater null {element} : replace an element by key if the new value is greater than the old one\n" +
                "remove_lower_key null : remove all elements whose key is less than given\n" +
                "group_counting_by_length : group the elements by the value of the \"length\" field, print the number of elements in each group\n" +
                "count_less_than_length length : print the number of elements whose \"length\" less than the given\n" +
                "filter_by_mpaa_rating mpaaRating : print an elements whose \"mpaaRating\" is equal to the given");
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
