package pub.cellebi.cli;

import java.util.Arrays;

public final class Commands {

    public static Command create(Class<? extends Command> parentClazz, String name) {
        Action.CliCommand cliCommand = parentClazz.getAnnotation(Action.CliCommand.class);
        Class<? extends Command> targetClass = findSubCommandClass(parentClazz, name);
        return createByTarget(targetClass);
    }

    public static Class<? extends Command> findSubCommandClass(Class<? extends Command> parentClazz, String cmd) {
        Action.CliCommand cliCommand = parentClazz.getAnnotation(Action.CliCommand.class);
        return Arrays.stream(cliCommand.subCommands())
                .filter(it -> it.getAnnotation(Action.CliCommand.class).name().equals(cmd))
                .findFirst()
                .orElse(null);
    }

    public static Command createByTarget(Class<? extends Command> targetClass) {
        Action.CliCommand cliCommand = targetClass.getAnnotation(Action.CliCommand.class);
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
