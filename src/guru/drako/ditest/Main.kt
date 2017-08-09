package guru.drako.ditest

import kotlin.system.exitProcess

class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val ctx = Context.configure {
        ConsoleWriter() provides Writer::class.named("stdout")
        HelloWorldProgram::class provides Executable::class with {
          "writer" aka "stdout"
        }
      }

      val app = ctx.resolve(Executable::class)
      val result = app.run(args)
      exitProcess(result)
    }
  }
}
