package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An object that describes some characteristic or attribute but carries no concept of identity. Though we are
 * typically talking of objects when referring to value types, native types are actually a good example of value
 * types. It is common to make value types immutable.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValueObject {
}
