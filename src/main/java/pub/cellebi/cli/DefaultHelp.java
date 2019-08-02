package pub.cellebi.cli;

import java.util.List;

@Action.CliCommand(name = "help", usage = "")
public final class DefaultHelp extends Command {


    @Override
    public void execute(List<String> params) {
        if (params.size() > 0) {
            String command = params.get(0);
            Commands.create(command).help();
        }
    }

    @Override
    public void initOptions() {
        options.postBoolOption("help", false, "show help usage");
    }
}
