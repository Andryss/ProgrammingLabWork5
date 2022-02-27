package Commands;

import ReadersExecutors.Executor;

/**
 * interface Command represents all required command methods
 */
public interface Command {
    /**
     * Makes command executing
     * @param state tells method "to validate" or "to execute"
     * @return true if method complete without troubles
     * @see ReadersExecutors.Executor.ExecuteState
     */
    boolean execute(Executor.ExecuteState state);

    /**
     * Validate and set arguments for command
     * @param args String array with arguments
     * @throws BadArgumentsException if arguments are incorrect
     */
    void setArgs(String... args) throws BadArgumentsException;
}
