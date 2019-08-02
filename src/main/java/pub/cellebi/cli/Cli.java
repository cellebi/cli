package pub.cellebi.cli;

import pub.cellebi.option.OptionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Cli {

    /**
     * 注册你的命令类
     *
     * @param clazz 注册的类
     */

    static {
        register(DefaultHelp.class);
    }

    public static void register(Class<? extends Command> clazz) {
        Command.CLASS_SET.add(clazz);
    }

    public static void register(Class<Command>[] clazzArray) {
        Command.CLASS_SET.addAll(Arrays.asList(clazzArray));
    }

    public static void run(String[] arguments) {
        List<String> args = new ArrayList<>(Arrays.asList(arguments));
        Command global = Commands.createGlobal();
        if (args.isEmpty()) {
            global.help();
        }
        if (args.get(0).startsWith("-")) {
            //firstly execute global command
            global.doExecute(args);
        }
        String cmd = args.remove(0);
        Commands.create(cmd).doExecute(args);
    }
}
