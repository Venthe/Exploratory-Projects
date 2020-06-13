package eu.venthe.dddcore.api.annotations.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 *
 * Repositories protect us from taking a data-centric view of our code. They allow us to persist and retrieve
 * aggregates without dealing directly with the underlying persistence. It is however important for developers to at
 * least be aware of the underlying implementations so as not to abuse the repository from a performance or scoping way.
 *
 * The abstraction of the repository is contained within the domain. This abstraction knows about the domain models
 * within that context. More specifically it knows about the aggregate that it is returning. A repository returns an
 * Entity (or collection of Entities) and the aggregate for wich that Entity is the Aggregate Root.
 *
 * The implementation of the repository abstraction does not reside in the domain. It is a Infrastructural concern
 * and can change. What is important though is that the repository handles mapping however the data is persisted into
 * a fully hydrated and consistent aggregate.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DomainRepository {
}
