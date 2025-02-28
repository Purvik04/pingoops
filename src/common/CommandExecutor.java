package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static constants.GlobalConstants.*;
import static constants.PingConstants.*;

public abstract class CommandExecutor
{
    private static final Logger LOGGER = Logger.getLogger(CommandExecutor.class.getName());

    public abstract String execute(String argument);

    public static Process process = null;

    public static Process spawnProcess(ArrayList<String> command) throws Exception {
        return new ProcessBuilder(command).redirectErrorStream(true).start();
    }

    public boolean isCommandAvailable(String command)
    {
        var commandOutput = new StringBuilder();

        try {
            process = spawnProcess(new ArrayList<>(Arrays.asList(COMMAND_EXECUTABLE_LOCATOR, command)));

            readStream(process, commandOutput);

            var completed = process.waitFor(PING_PROCESS_TIMEOUT, TimeUnit.SECONDS);

            if (!completed)
            {
                process.destroyForcibly();

                LOGGER.log(Level.WARNING, PING_CHECK_TIMEOUT_ERROR);

                return false;
            }

            int exitCode = process.exitValue();

            if (exitCode == 0) {
                if(commandOutput.toString().contains("ping")){
                    return true;
                }
                else{
                    LOGGER.severe(COMMAND_NOT_FOUND_ERROR);
                    return false;
                }
            } else {
                return false;
            }
        }
        catch (Exception exception)
        {
            LOGGER.log(Level.SEVERE, exception.getMessage());

            return false;
        }
        finally
        {
            if (process != null && process.isAlive())
            {
                process.destroyForcibly();
            }
        }
    }

    protected static void readStream(Process process, StringBuilder commandOutput)
    {
        try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
        {
            var readLine = EMPTY_STRING;
            while ((readLine = reader.readLine()) != null)
            {
                commandOutput.append(readLine.trim()).append(NEWLINE_SEPARATOR);
            }
        }
        catch (Exception exception)
        {
            LOGGER.log(Level.SEVERE,exception.getMessage());
        }
    }
}
