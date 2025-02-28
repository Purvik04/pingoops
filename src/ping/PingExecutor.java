package ping;

import common.CommandExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static constants.GlobalConstants.*;
import static constants.PingConstants.*;

public class PingExecutor extends CommandExecutor
{
    @Override
    public String execute(String host)
    {
        var commandOutput = new StringBuilder();

        try
        {
            process = spawnProcess(new ArrayList<>(Arrays.asList(PING_COMMAND, PACKET_COUNT_FLAG, String.valueOf(PACKET_COUNT), host)));

            readStream(process, commandOutput);

            var completed = process.waitFor(10, TimeUnit.MILLISECONDS);

            if (!completed)
            {
                process.destroyForcibly();
                return !commandOutput.isEmpty() ? commandOutput.append(SECTION_DIVIDER).toString() : String.format("%s%s%s", PING_TIMEOUT_FOR_HOST_ERROR, host, SECTION_DIVIDER);
            }

            if (process.exitValue() == 0)
            {
                return extractKPI(commandOutput.toString(), host);
            }

            return String.format("%s%s%s", PING_COMMAND_EXECUTION_ERROR,host,SECTION_DIVIDER);

        }
        catch (Exception exception)
        {
            return String.format("%s%s%s%s", PING_COMMAND_EXECUTION_ERROR, host, exception.getMessage(), SECTION_DIVIDER);
        }
        finally
        {
            if (process != null && process.isAlive())
            {
                process.destroyForcibly();
            }
        }
    }

    private String extractKPI(String commandOutput, String host)
    {
        if (commandOutput == null || commandOutput.isEmpty())
        {
            return String.format("%s%s%s",PING_OUTPUT_EMPTY_ERROR,host, SECTION_DIVIDER);
        }

        var packetLossMatcher = PACKET_LOSS_PATTERN.matcher(commandOutput);

        var packetRTTMatcher = PACKET_RTT_PATTERN.matcher(commandOutput);

        var result = new StringBuilder(host).append(NEWLINE_SEPARATOR);

        if (packetLossMatcher.find() && packetRTTMatcher.find())
        {
            result.append(PACKET_LOSS_LABEL).append(Integer.parseInt(packetLossMatcher.group(1))).append(PACKET_LOSS_PERCENTAGE_LABEL).append(NEWLINE_SEPARATOR);

            result.append(RTT_MIN_LABEL).append(packetRTTMatcher.group(2)).append(RTT_UNIT_MILISECONDS).append(NEWLINE_SEPARATOR);

            result.append(RTT_AVG_LABEL).append(packetRTTMatcher.group(3)).append(RTT_UNIT_MILISECONDS).append(NEWLINE_SEPARATOR);

            result.append(RTT_MAX_LABEL).append(packetRTTMatcher.group(4)).append(RTT_UNIT_MILISECONDS).append(SECTION_DIVIDER);
        }
        else
        {
            result.append(PING_OUTPUT_EMPTY_ERROR).append(host).append(NEWLINE_SEPARATOR);
        }

        return result.toString();
    }
}