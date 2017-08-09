package guru.drako.ditest

class InstanceProvider<T: Any>(val instance: T): Provider<T> {
  override fun provideInstance(): T {
    return instance
  }
}
