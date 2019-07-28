package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An Assertion about some design element that must be true at all times, except during specifically transient
 * situations such as the middle of the execution of a method, or the middle of an uncommitted database transaction.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Invariant {
}
