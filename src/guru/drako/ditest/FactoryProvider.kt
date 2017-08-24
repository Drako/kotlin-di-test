package guru.drako.ditest

import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmErasure

class FactoryProvider<T: Any>(val context: Context, val clazz: KClass<T>): Provider<T> {
  private val aliases = mutableMapOf<String, String>()

  override fun provideInstance(): T {
    val sortedConstructors = clazz.constructors.sortedBy { ctor -> ctor.parameters.size }
    for (ctor in sortedConstructors) {
      val args = try {
        ctor.parameters.map { arg ->
          try {
            val name = aliases[arg.name.orEmpty()].orEmpty()
            context.resolve(arg.type.jvmErasure, name)
          } catch (ex: RuntimeException) {
            if (arg.type.isMarkedNullable) null else throw ex
          }
        }.toTypedArray()
      }
      catch (ex: RuntimeException) {
        null
      }

      if (args != null) {
        return ctor.call(*args)
      }
    }

    throw RuntimeException("Could not resolve $clazz!")
  }

  infix fun String.providedBy(name: String) {
    aliases.put(this, name)
  }
}
