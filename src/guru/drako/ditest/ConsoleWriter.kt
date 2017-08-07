package guru.drako.ditest

class ConsoleWriter: Writer {
  override fun write(message: String) {
    print(message)
  }
}
