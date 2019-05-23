package di

/**
 * Created by Sonphil on 18-05-19.
 *
 * Identifies a type that the injector only instantiates once.
 */

@UseExperimental(ExperimentalMultiplatform::class)
@OptionalExpectation
@Retention(AnnotationRetention.RUNTIME)
expect annotation class Singleton()