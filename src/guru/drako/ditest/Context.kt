package guru.drako.ditest

import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmErasure

class Context {
  companion object {
    inline fun configure(handler: Context.() -> Unit): Context {
      val ctx = Context()
      ctx.configure(handler)
      return ctx
    }
  }

  data class BindingKey(val requested: KClass<out Any>, val name: String = "")

  private val bindings = mutableMapOf<BindingKey, Provider<Any>>()

  inline fun configure(handler: Context.() -> Unit) {
    this.handler()
  }

  infix fun <Requested: Any, Resolved: Requested> KClass<Resolved>.provides(requested: KClass<Requested>):
      FactoryProvider<Resolved> {
    val provider = FactoryProvider(this@Context, this)
    bindings.put(BindingKey(requested), provider)
    return provider
  }

  infix fun <Requested: Any, Resolved: Requested> KClass<Resolved>.provides(requested: Aliased):
      FactoryProvider<Resolved> {
    val provider = FactoryProvider(this@Context, this)
    bindings.put(BindingKey(requested.requested, requested.name), provider)
    return provider
  }

  inline infix fun <Resolved: Any> FactoryProvider<Resolved>.with(handler: FactoryProvider<Resolved>.() -> Unit) {
    this.handler()
  }

  infix fun <Resolved: Any> Resolved.provides(requested: KClass<out Resolved>) {
    bindings.put(BindingKey(requested), InstanceProvider(this))
  }

  infix fun <Resolved: Any> Resolved.provides(requested: Aliased) {
    bindings.put(BindingKey(requested.requested, requested.name), InstanceProvider(this))
  }

  infix fun <T: Any> KClass<T>.named(name: String) = Aliased(this, name)

  fun <T: Any> resolve(clazz: KClass<T>, name: String = ""): T {
    val provider = bindings[BindingKey(clazz, name)] ?: FactoryProvider(this, clazz)

    @Suppress("UNCHECKED_CAST")
    return provider.provideInstance() as T
  }
}
