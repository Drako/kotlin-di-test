package guru.drako.ditest

import kotlin.system.exitProcess

class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val lang = if (args.isNotEmpty()) args[0] else "en"

      val ctx = Context.configure {
        ConsoleWriter() provides Writer::class.byName("stdout")

        // name of instances is comfortably set via @Named on the classes themselves
        // it can still be overwritten using .byName as shown above.
        EnglishMessageSource() provides MessageSource::class
        GermanMessageSource() provides MessageSource::class

        HelloWorldProgram::class provides Executable::class with {
          "writer" providedBy "stdout"
          "messageSource" providedBy lang
        }
      }

      val app = ctx.resolve(Executable::class)
      val result = app.run(args)
      exitProcess(result)
    }
  }
}
