package guru.drako.ditest

class GermanMessageSource: MessageSource {
  override fun getMessage(id: String): String? =
      when (id) {
        "hello" -> "Hallo Welt!"
        else -> null
      }
}
