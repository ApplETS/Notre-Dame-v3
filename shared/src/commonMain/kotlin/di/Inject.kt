package di

/**
 * Identifies injectable constructors, methods, and fields.
 *
 * Created by Sonphil on 01-03-19.
 */

@UseExperimental(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
expect annotation class Inject()