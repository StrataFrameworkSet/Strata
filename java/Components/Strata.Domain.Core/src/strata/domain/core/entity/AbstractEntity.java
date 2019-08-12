//////////////////////////////////////////////////////////////////////////////
// AbstractEntity.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.entity;

import java.time.Instant;

public abstract
class AbstractEntity<K>
    implements IEntity<K>
{
    private K       itsPrimaryId;
    private Integer itsVersion;
    private Instant itsCreated;
    private Instant itsLastModified;

    /*************************************************************************
     * Creates a new instance of {@code AbstractEntity<K>}.
     */
    protected
    AbstractEntity()
    {
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
