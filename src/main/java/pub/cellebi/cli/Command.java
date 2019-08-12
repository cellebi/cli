package pub.cellebi.cli;

import pub.cellebi.option.Option;
import pub.cellebi.option.OptionSet;
import pub.cellebi.option.type.*;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author makhocheung
 */
public abstract class Command implements Action {

    private final OptionSet options;
    protected String name;
    protected Command parent;


    protected Command() {
        CliCommand cliCommand = this.getClass().getAnnotation(CliCommand.class);
        name = cliCommand.name();
        String usage = cliCommand.usage();
        options = new OptionSet(name, usage);
        initOptions();
    }

    protected Command parse(List<String> args) {
        options.parse(args);
        return this;
    }

    protected Command byteOption(String name, byte value, String usage) {
        options.postByteOption(name, value, usage);
        return this;
    }

    protected Command shortOption(String name, short value, String usage) {
        options.postShortOption(name, value, usage);
        return this;
    }

    protected Command intOption(String name, int value, String usage) {
        options.postIntOption(name, value, usage);
        return this;
    }

    protected Command longOption(String name, long value, String usage) {
        options.postLongOption(name, value, usage);
        return this;
    }

    protected Command floatOption(String name, float value, String usage) {
        options.postFloatOption(name, value, usage);
        return this;
    }

    protected Command doubleOption(String name, double value, String usage) {
        options.postDoubleOption(name, value, usage);
        return this;
    }

    protected Command boolOption(String name, boolean value, String usage) {
        options.postBoolOption(name, value, usage);
        return this;
    }

    protected Command stringOption(String name, String value, String usage) {
        options.postStringOption(name, value, usage);
        return this;
    }

    protected Command customOption(Supplier<? extends Option> supplier) {
        options.postCustomOption(supplier);
        return this;
    }

    public void doExecute(List<String> params) {
        before();
        execute(params);
        after();
    }

    public String getName() {
        return name;
    }

    public Command parent() {
        return this.parent;
    }

    public Command global() {
        Command command = this;
        while (command.parent != command) {
            command = command.parent;
        }
        return command;
    }

    public Command findParent(Class<? extends Command> clazz) {
        Command target = this;
        Command temp = this.parent;
        while (temp != this) {
            if (temp.getClass().isInstance(clazz)) {
                target = temp;
                break;
            }
            temp = temp.parent;
        }
        return target;
    }

    public boolean hasSubCommands() {
        Class<? extends Command> clazz = this.getClass();
        Action.CliCommand cliCommand = clazz.getAnnotation(Action.CliCommand.class);
        return cliCommand.subCommands().length > 0;
    }

    public void help() {
        System.out.println(options.usage());
    }

    public Command getParent() {
        return parent;
    }

    public void setParent(Command parent) {
        if (this.parent != null) {
            throw new IllegalStateException();
        }
        this.parent = parent;
    }

    public String peek() {
        return options.peek();
    }

    public ByteOption byteOption(String name) {
        return options.getByteOption(name);
    }

    public ShortOption shortOption(String name) {
        return options.getShortOption(name);
    }

    public IntOption intOption(String name) {
        return options.getIntOption(name);
    }

    public LongOption longOption(String name) {
        return options.getLongOption(name);
    }

    public FloatOption floatOption(String name) {
        return options.getFloatOption(name);
    }

    public DoubleOption doubleOption(String name) {
        return options.getDoubleOption(name);
    }

    public BoolOption boolOption(String name) {
        return options.getBoolOption(name);
    }

    public StringOption stringOption(String name) {
        return options.getStringOption(name);
    }

    public <T extends Option> T customOption(String name, Class<T> type) {
        return options.getOptionByType(name, type);
    }

    public byte byteVal(String name) {
        return byteOption(name).getValue();
    }

    public short shortVal(String name) {
        return shortOption(name).getValue();
    }

    public int intVal(String name) {
        return intOption(name).getValue();
    }

    public long longVal(String name) {
        return longOption(name).getValue();
    }

    public double doubleVal(String name) {
        return doubleOption(name).getValue();
    }

    public float floatVal(String name) {
        return floatOption(name).getValue();
    }

    public boolean boolVal(String name) {
        return boolOption(name).getValue();
    }

    public String stringVal(String name) {
        return stringOption(name).getValue();
    }

    public <T extends Option, E> E customVal(String name, Class<T> optionType, Class<E> valueType) {
        return valueType.cast(customOption(name, optionType).getValue());
    }

    /**
     * define the your command options by overwriting this method
     *
     * {@code void initOptions(){
     *    boolOption("help",false,"xxx");
     * }}
     */
    protected void initOptions() {
    }
}
