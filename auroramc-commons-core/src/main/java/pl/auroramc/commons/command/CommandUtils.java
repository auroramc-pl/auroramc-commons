package pl.auroramc.commons.command;

import static java.util.Locale.ROOT;

public final class CommandUtils {

  private static final String ARGUMENT_SEPARATOR = " ";
  private static final String COMMAND_NAMESPACE_SEPARATOR = ":";

  private CommandUtils() {}

  public static String resolveCommand(final String query) {
    final String[] parts = query.split(ARGUMENT_SEPARATOR);
    if (parts.length == 0) {
      throw new CommandResolvingException("Could not resolve command, because the query is empty.");
    }

    final String prefixedCommand = parts[0];
    final String[] slicesOfCommand = prefixedCommand.split(COMMAND_NAMESPACE_SEPARATOR);
    return slicesOfCommand[slicesOfCommand.length - 1].toLowerCase(ROOT);
  }
}