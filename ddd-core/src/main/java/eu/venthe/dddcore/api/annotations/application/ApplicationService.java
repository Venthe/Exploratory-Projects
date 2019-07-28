package eu.venthe.dddcore.api.annotations.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * APPLICATION SERVICES are the interface used by the outside world (Application API), where the outside world can’t
 * communicate via our ENTITY OBJECTS, but may have other representations of them. APPLICATION SERVICES could map
 * outside messages to internal operations and processes, communicating with services in the Domain and
 * Infrastructure layers to provide cohesive operations for outside clients. Messaging patterns tend to rule
 * APPLICATION SERVICES, as the other service layers don’t have a reference back out to the APPLICATION SERVICES.
 * Business rules are not allowed in an APPLICATION SERVICE, those belong in the Domain layer.
 *
 * APPLICATION SERVICE should in essence be an Operation Script, that contain application logic consisting of
 * conditional, iteration (i.e. looping), and sequencing statements. However, the bulk of the domain logic resides in
 * external Domain Layer objects.
 *
 * Classes in this layer should be tested via end-to-end tests as the unit tests would require mocking a lot of
 * dependencies without a huge payoff.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApplicationService {
}
