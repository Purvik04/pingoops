package constants;

import ping.PingConfigManager;
import java.util.regex.Pattern;

import static constants.GlobalConstants.IS_WINDOWS_OS;

public class PingConstants
{
    public static final Pattern PACKET_LOSS_PATTERN = Pattern.compile("(\\d+)% packet loss");

    public static final Pattern PACKET_RTT_PATTERN = Pattern.compile("(rtt|Minimum|Avg|Maximum).*?([0-9.]+).*?([0-9.]+).*?([0-9.]+)");

    public static final String PING_COMMAND = "ping";

    public static final String PACKET_COUNT_FLAG = IS_WINDOWS_OS ? "-n" : "-c";

    public static final int DEFAULT_PACKET_COUNT = 5;

    public static final int DEFAULT_PING_PROCESS_TIMEOUT = 5;

    public static final int PACKET_COUNT = Integer.parseInt(PingConfigManager.get("ping.packet.count", String.valueOf(DEFAULT_PACKET_COUNT)));

    public static final int PING_PROCESS_TIMEOUT = Integer.parseInt(PingConfigManager.get("ping.timeout", String.valueOf(DEFAULT_PING_PROCESS_TIMEOUT)));

    public static final String PING_CHECK_TIMEOUT_ERROR = "Ping availability check took too long and was terminated.";

    public static final String PING_TIMEOUT_FOR_HOST_ERROR = "Error: Ping process timed out for ";

    public static final String PING_OUTPUT_EMPTY_ERROR= "Error: No output received from ping command for ";

    public static final String PING_COMMAND_EXECUTION_ERROR = "Error executing ping for ";

    public static final String PACKET_LOSS_PERCENTAGE_LABEL =  "%";

    public static final String RTT_UNIT_MILISECONDS = " ms";

    public static final String PACKET_LOSS_LABEL = "Packet Loss: ";

    public static final String RTT_MIN_LABEL = "RTT MIN : ";

    public static final String RTT_AVG_LABEL = "RTT AVG : ";

    public static final String RTT_MAX_LABEL = "RTT MAX : ";

}
