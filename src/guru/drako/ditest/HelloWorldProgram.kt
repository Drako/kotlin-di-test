package guru.drako.ditest

class HelloWorldProgram(val writer: Writer, val messageSource: MessageSource): Executable {
  override fun run(args: Array<String>): Int {
    writer.writeln(messageSource.getMessage("hello") ?: "Hello world")
    return 0
  }
}
