package guru.drako.ditest

@Target(AnnotationTarget.CLASS, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FIELD)
annotation class Named(val name: String)
