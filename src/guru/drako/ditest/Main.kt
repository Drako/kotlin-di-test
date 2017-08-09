package guru.drako.ditest

import kotlin.system.exitProcess

class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val lang = if (args.isNotEmpty()) args[0] else "en"

      val ctx = Context.configure {
        ConsoleWriter() provides Writer::class.named("stdout")

        EnglishMessageSource() provides MessageSource::class.named("en")
        GermanMessageSource() provides MessageSource::class.named("de")

        HelloWorldProgram::class provides Executable::class with {
          "writer" aka "stdout"
          "messageSource" aka lang
        }
      }

      val app = ctx.resolve(Executable::class)
      val result = app.run(args)
      exitProcess(result)
    }
  }
}
