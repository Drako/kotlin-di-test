package guru.drako.ditest

class ConsoleWriter: Writer {
  override fun writeln(message: String) {
    println(message)
  }
}
