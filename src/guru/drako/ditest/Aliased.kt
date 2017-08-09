package guru.drako.ditest

import kotlin.reflect.KClass

data class Aliased(val requested: KClass<out Any>, val name: String)
