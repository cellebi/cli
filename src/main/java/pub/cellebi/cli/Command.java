package pub.cellebi.cli;

import pub.cellebi.option.Option;
import pub.cellebi.option.OptionSet;
import pub.cellebi.option.type.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public abstract class Command implements Action {

    static final Set<Class<? extends Command>> CLASS_SET = new HashSet<>();

    protected String name;
    protected final OptionSet options;


    protected Command() {
        CliCommand cliCommand = this.getClass().getAnnotation(CliCommand.class);
        String name = cliCommand.name();
        String usage = cliCommand.usage();
        options = new OptionSet(name, usage);
        initOptions();
    }

    public void doExecute(List<String> args) {
        options.parse(args);
        before();
        execute(args);
        after();
    }


    public String getName() {
        return name;
    }

    public Command setName(String name) {
        this.name = name;
        return this;
    }

    protected Command parse(List<String> args) {
        options.parse(args);
        return this;
    }

    protected void help() {
        System.out.println(options.usage());
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

    protected ByteOption byteOption(String name) {
        return options.getByteOption(name);
    }

    protected ShortOption shortOption(String name) {
        return options.getShortOption(name);
    }

    protected IntOption intOption(String name) {
        return options.getIntOption(name);
    }

    protected LongOption longOption(String name) {
        return options.getLongOption(name);
    }

    protected FloatOption floatOption(String name) {
        return options.getFloatOption(name);
    }

    protected DoubleOption doubleOption(String name) {
        return options.getDoubleOption(name);
    }

    protected BoolOption boolOption(String name) {
        return options.getBoolOption(name);
    }

    protected StringOption stringOption(String name) {
        return options.getStringOption(name);
    }

    protected <T extends Option> T customOption(String name, Class<T> type) {
        return options.getOptionByType(name, type);
    }

    protected byte byteVal(String name) {
        return byteOption(name).getValue();
    }

    protected short shortVal(String name) {
        return shortOption(name).getValue();
    }

    protected int intVal(String name) {
        return intOption(name).getValue();
    }

    protected long longVal(String name) {
        return longOption(name).getValue();
    }

    protected double doubleVal(String name) {
        return doubleOption(name).getValue();
    }

    protected float floatVal(String name) {
        return floatOption(name).getValue();
    }

    protected boolean boolVal(String name) {
        return boolOption(name).getValue();
    }

    protected String stringVal(String name) {
        return stringOption(name).getValue();
    }

    protected <T extends Option, E> E customVal(String name, Class<T> optionType, Class<E> valueType) {
        return valueType.cast(customOption(name, optionType).getValue());
    }

    /**
     * define the your command options
     */
    void initOptions() {
    }
}
