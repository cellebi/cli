package pub.cellebi.cli;

import pub.cellebi.option.Option;

import java.lang.annotation.*;
import java.util.*;

public interface Action {

    default void before() {
    }

    default void after() {
    }

    void execute(List<String> params);

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CliCommand {
        String name() default "";

        String usage() default "";

        Class<? extends Command>[] subCommands() default {};

    }

    @Target(ElementType.METHOD)
    @interface ExceptionHandler {

    }

}
