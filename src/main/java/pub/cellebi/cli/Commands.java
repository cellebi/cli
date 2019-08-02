package pub.cellebi.cli;

public final class Commands {

    private static Command create(String name, boolean isGlobal) {
        Class<? extends Command> commandClass = Command.CLASS_SET.stream().filter(clazz -> {
            Action.CliCommand cliCommand = clazz.getAnnotation(Action.CliCommand.class);
            boolean predict;
            if (isGlobal) {
                predict = cliCommand.isGlobal();
            } else {
                predict = cliCommand.name().equals(name);
            }
            return predict;
        }).findFirst().orElse(null);
        try {
            return commandClass.newInstance().setName(name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Command create(String name) {
        return create(name, false);
    }

    public static Command createGlobal() {
        return create("", true);
    }
}
