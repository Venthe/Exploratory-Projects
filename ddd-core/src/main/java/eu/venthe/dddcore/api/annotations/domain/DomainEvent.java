package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A domain object that defines an event (something that happened). A domain event is an event that domain experts
 * care about. An event represents something that took place in the domain. Since an event represents something in
 * the past, it can be considered as a fact and used to take decisions in other parts of the system.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DomainEvent {
}
