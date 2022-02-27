package Commands;

import ReadersExecutors.Executor;

import java.util.List;

/**
 * Command, which prints last 13 commands (without arguments)
 * @see NameableCommand
 * @see Command
 */
public class HistoryCommand extends NameableCommand {
    /**
     * List with all successful executed commands
     */
    private final List<String> history;

    public HistoryCommand(String commandName, List<String> history) {
        super(commandName);
        this.history = history;
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
        System.out.println("*history*:");
        for (int i = Math.max(0, history.size() - 13); i < history.size(); i++) {
            System.out.println(history.get(i));
        }
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
