package guru.drako.ditest

@Named("en")
class EnglishMessageSource: MessageSource {
  override fun getMessage(id: String): String? =
      when (id) {
        "hello" -> "Hello world"
        else -> null
      }
}
