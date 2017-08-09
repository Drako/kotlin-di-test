package guru.drako.ditest

class EnglishMessageSource: MessageSource {
  override fun getMessage(id: String): String? =
      when (id) {
        "hello" -> "Hello world"
        else -> null
      }
}
