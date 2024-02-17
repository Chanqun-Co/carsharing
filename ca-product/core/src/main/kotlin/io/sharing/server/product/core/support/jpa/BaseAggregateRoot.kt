package io.sharing.server.core.support.jpa

import jakarta.persistence.MappedSuperclass
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import org.springframework.util.Assert
import java.util.*

@MappedSuperclass
abstract class BaseAggregateRoot<A : BaseAggregateRoot<A>> : BaseEntity() {
    @Transient
    private val domainEvents: MutableList<Any> = ArrayList()

    /**
     * Registers the given event object for publication on a call to a Spring Data repository's save methods.
     *
     * @param event must not be null.
     * @return the event that has been added.
     * @see .andEvent
     */
    protected fun <T : Any> registerEvent(event: T): T {
        Assert.notNull(event, "Domain event must not be null!")
        domainEvents.add(event)
        return event
    }

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    @AfterDomainEventPublication
    protected fun clearDomainEvents() {
        domainEvents.clear()
    }

    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    protected fun domainEvents(): Collection<Any>? {
        return Collections.unmodifiableList(domainEvents)
    }

    /**
     * Adds all events contained in the given aggregate to the current one.
     *
     * @param aggregate must not be null.
     * @return the aggregate
     */
    protected fun andEventsFrom(aggregate: A): A {
        Assert.notNull(aggregate, "Aggregate must not be null!")
        domainEvents.addAll(aggregate.domainEvents()!!)
        return this as A
    }

    /**
     * Adds the given event to the aggregate for later publication when calling a Spring Data repository's save-method.
     * Does the same as [.registerEvent] but returns the aggregate instead of the event.
     *
     * @param event must not be null.
     * @return the aggregate
     * @see .registerEvent
     */
    protected fun andEvent(event: Any): A {
        registerEvent(event)
        return this as A
    }
}
