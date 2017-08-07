package guru.drako.ditest

class HelloWorldProgram(val writer: Writer): Executable {
  override fun run(args: Array<String>): Int {
    writer.write("Hello world!\n")
    return 0
  }
}
