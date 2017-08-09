package guru.drako.ditest

interface Provider<out T> {
  fun provideInstance(): T
}
