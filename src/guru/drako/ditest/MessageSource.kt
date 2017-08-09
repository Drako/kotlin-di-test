package guru.drako.ditest

interface MessageSource {
  fun getMessage(id: String): String?
}
