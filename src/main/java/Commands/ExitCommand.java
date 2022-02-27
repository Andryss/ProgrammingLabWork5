package Commands;

import ReadersExecutors.Executor;

/**
 * Command, which ends the program (without saving)
 * @see NameableCommand
 * @see Command
 */
public class ExitCommand extends NameableCommand {

    public ExitCommand(String commandName) {
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
            throw new RuntimeException("Something wrong here!");
        }
        System.exit(0);
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
