package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When an operation does not conceptually belong to any object. Following the natural contours of the problem, you
 * can implement these operations in services.
 *
 * When modeling sometimes an operation or workflow doesn't fit into the current model. Usually this just means you
 * are not accurately capturing the model you need to represent the business problem but every now and again it is
 * valid to place this operation in a domain service. If placing a workflow comflates your model objects maybe a
 * service is the way to go. Services are represented by verbs rather than nouns and speak to what the DO. An
 * important distinction from model objects is that they are completely stateless. A service will take various other
 * domain objects and execute some action, possibly returning some result.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DomainService {
}
