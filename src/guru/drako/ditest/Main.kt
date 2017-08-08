package guru.drako.ditest

import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmErasure
import kotlin.system.exitProcess

class Main {
  object Context {
    private var binding: MutableMap<KClass<out Any>, KClass<out Any>> = mutableMapOf()

    fun <Requested: Any, Resolved: Requested> bind(requested: KClass<Requested>, resolved: KClass<Resolved>)
      = binding.put(requested, resolved)

    fun <T: Any> resolve(clazz: KClass<T>): T {
      val actualType = binding[clazz]
      if (actualType != null) {
        // I have no clue how to do that cleanly
        // the obvious solutions don't work
        // also the cast should never fail as the bind function only accepts parameters that fulfill the requirements
        @Suppress("UNCHECKED_CAST")
        return resolve(actualType as KClass<T>)
      }

      val sortedConstructors = clazz.constructors.sortedBy { ctor -> ctor.parameters.size }
      for (ctor in sortedConstructors) {
        val args = try {
          ctor.parameters.map { arg ->
            try {
              resolve(arg.type.jvmErasure)
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
  }

  companion object {

    @JvmStatic
    fun main(args: Array<String>) {
      Context.bind(Writer::class, ConsoleWriter::class)
      Context.bind(Executable::class, HelloWorldProgram::class)

      val app = Context.resolve(Executable::class)
      val result = app.run(args)
      exitProcess(result)
    }
  }
}
