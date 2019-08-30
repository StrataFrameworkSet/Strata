//////////////////////////////////////////////////////////////////////////////
// AbstractEntity.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.entity;

import strata.domain.core.domainevent.AbstractDomainEventSource;
import strata.domain.core.domainevent.IDomainEvent;
import strata.domain.core.domainevent.IDomainEventObserver;
import strata.domain.core.domainevent.IDomainEventSource;

import java.time.Instant;

public abstract
class AbstractEntityAndDomainEventSource<
    K,
    S extends IDomainEventSource<S,E,O>,
    E extends IDomainEvent<S>,
    O extends IDomainEventObserver<E>>
    extends AbstractDomainEventSource<S,E,O>
    implements IEntity<K>,IDomainEventSource<S,E,O>
{
    private K       itsPrimaryId;
    private Integer itsVersion;
    private Instant itsCreated;
    private Instant itsLastModified;

    /*************************************************************************
     * Creates a new instance of
     * {@code AbstractEntityAndDomainEventSource<K,S,E,O>}.
     */
    protected
    AbstractEntityAndDomainEventSource()
    {
        itsVersion      = null;
        itsCreated      = Instant.now();
        itsLastModified = Instant.now();
    }

    /*************************************************************************
     * Creates a new instance of
     * {@code AbstractEntityAndDomainEventSource<K,S,E,O>}.
     */
    protected
    AbstractEntityAndDomainEventSource(
        AbstractEntityAndDomainEventSource<K,S,E,O> other)
    {
        super(other);

        itsVersion      = null;
        itsCreated      = Instant.now();
        itsLastModified = Instant.now();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IEntity<K>
    setPrimaryId(K primaryId)
    {
        itsPrimaryId = primaryId;
        return this;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IEntity<K>
    setVersion(Integer version)
    {
        itsVersion = version;
        return this;
    }

    /*************************************************************************
     * {@inheritDoc}
     * @param created
     */
    @Override
    public IEntity<K>
    setCreated(Instant created)
    {
        itsCreated = created;
        return this;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IEntity<K>
    setLastModified(Instant lastModified)
    {
        itsLastModified = lastModified;
        return this;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public K
    getPrimaryId()
    {
        return itsPrimaryId;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public Integer
    getVersion()
    {
        return itsVersion;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public Instant
    getCreated()
    {
        return itsCreated;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public Instant
    getLastModified()
    {
        return itsLastModified;
    }
}

//////////////////////////////////////////////////////////////////////////////
