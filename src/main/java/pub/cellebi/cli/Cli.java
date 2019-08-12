package pub.cellebi.cli;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Cli {


    public static void run(Class<? extends Command> globalClass, String[] arguments) {
        List<String> argsWithOptions = new ArrayList<>(Arrays.asList(arguments));
        Command target = parseCommandLine(globalClass, argsWithOptions);
        target.doExecute(argsWithOptions);
    }

    private static Command parseCommandLine(Class<? extends Command> globalClass, List<String> args) {
        Command root = Commands.createByTarget(globalClass).parse(args), target = root;
        root.parent = target;
        if (args.isEmpty()) {
            return target;
        }
        while (!args.isEmpty() && target.hasSubCommands()) {
            String cmd = args.get(0);
            Command command = Commands.create(target.getClass(), cmd);
            if (command == null) {
                return target;
            }
            args.remove(0);
            command.parse(args);
            command.parent = target;
            target = command;
        }
        return target;
    }
}
