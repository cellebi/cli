package pub.cellebi.cli;

import java.util.List;

@Action.CliCommand(name = "help")
public final class DefaultHelp extends Command {

    //只能help二级命令
    @Override
    public void execute(List<String> params) {
        Class<? extends Command> parentCommandClass = this.global().getClass();
        Class<? extends Command> targetCommandClass = null;
        for (String param : params) {
            targetCommandClass = Commands.findSubCommandClass(parentCommandClass, param);
            if (targetCommandClass == null) {
                break;
            }
            parentCommandClass = targetCommandClass;
        }
        if (targetCommandClass == null) {
            throw new IllegalArgumentException();
        }
        Commands.createByTarget(targetCommandClass).help();
    }

    @Override
    public void initOptions() {
        boolOption("help", false, "show help usage");
    }
}
