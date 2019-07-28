package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A cluster of associated objects that are treated as a unit for the purpose of data changes. External references
 * are restricted to single DOMAIN ENTITY of the AGGREGATE, designated as the AGGREGATE ROOT. A set of consistency
 * rules applies
 * within the AGGREGATE’S boundaries.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AggregateRoot {
}
