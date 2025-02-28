import common.CommandExecutor;
import ping.PingExecutor;

import java.util.logging.Logger;

import static constants.GlobalConstants.*;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            Logger.getLogger(Main.class.getName()).warning("Usage: java Main <command> <arg1> <arg2> ...");
            return;
        }

        String command = args[0].toLowerCase();
        CommandExecutor executor;

        switch (command)
        {
            case "ping":
                executor = new PingExecutor();
                if(executor.isCommandAvailable(command))
                {
                    for(int i = 1; i< args.length; i++)
                    {
                        System.out.println(executor.execute(args[i]));;
                    }
                }
                break;

            default:
                System.out.println("Unsupported command: " + command);
        }

    }
}