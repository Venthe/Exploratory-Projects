package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Since an aggregate should always be in a consistent state it is important that they are constructed in a
 * consistent state to the user. Factories provide a way to ensure that new instances of an aggregate always start in
 * a consistent state.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DomainFactory {
}
