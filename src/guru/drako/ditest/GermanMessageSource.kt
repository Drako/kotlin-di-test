package guru.drako.ditest

@Named("de")
class GermanMessageSource: MessageSource {
  override fun getMessage(id: String): String? =
      when (id) {
        "hello" -> "Hallo Welt!"
        else -> null
      }
}
