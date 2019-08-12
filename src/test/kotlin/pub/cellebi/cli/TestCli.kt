package pub.cellebi.cli

import org.junit.Test
import java.lang.IllegalArgumentException

class TestCli {

    @Test
    fun test() {
        Cli.run(MyTest::class.java, arrayOf("say", "--lang", "en"))
        Cli.run(MyTest::class.java, arrayOf("help", "say"))
    }
}

@Action.CliCommand(name = "test", usage = "test unit", subCommands = [SayHi::class, DefaultHelp::class])
class MyTest : Command() {
    override fun execute(params: MutableList<String>?) {
        help()
    }
}

@Action.CliCommand(name = "say", usage = "say hi in different language")
class SayHi : Command() {

    override fun execute(params: MutableList<String>?) {
        when (stringVal("lang")) {
            "ch" -> println("你好")
            "en" -> println("Hi")
            else -> throw IllegalArgumentException()
        }
    }

    override fun initOptions() {
        stringOption("lang", "ch", "specific the language, like ch,en,etc")
    }
}