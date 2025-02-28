package constants;

public class GlobalConstants {
    public static final String SECTION_DIVIDER = "\n-----------------------------------";

    public static final String NEWLINE_SEPARATOR = "\n";

    public static String EMPTY_STRING = "";

    public static boolean IS_WINDOWS_OS = System.getProperty("os.name").toLowerCase().contains("win");

    public static final String COMMAND_EXECUTABLE_LOCATOR = IS_WINDOWS_OS ? "where" : "which";

    public static final String COMMAND_NOT_FOUND_ERROR = "Error: command not found. Please install it.";
}
