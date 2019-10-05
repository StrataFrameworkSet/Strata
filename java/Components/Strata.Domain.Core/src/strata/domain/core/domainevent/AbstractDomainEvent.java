//////////////////////////////////////////////////////////////////////////////
// AbstractDomainEvent.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.domainevent;

import java.time.Instant;
import java.util.UUID;

/*****************************************************************************
 * Base implementation of {@code IDomainEvent<S>} interface.
 *
 * @param <S> event source type
 */
public abstract
class AbstractDomainEvent<S>
    implements IDomainEvent<S>
{
    private final String  name;
    private final UUID    correlationId;
    private final Instant timestamp;
    private final S       source;

    /*************************************************************************
     * Creates a new instance of {@code AbstractDomainEvent<S>}.
     *
     * @param nm  event name
     * @param src event source
     */
    protected
    AbstractDomainEvent(String nm,S src)
    {
        this(nm,null,src);
    }

    /*************************************************************************
     * Creates a new instance of {@code AbstractDomainEvent<S>}.
     *
     * @param nm  event name
     * @param correlId correlation id
     * @param src event source
     */
    protected
    AbstractDomainEvent(String nm,UUID correlId,S src)
    {
        name = nm;
        correlationId = correlId;
        timestamp = Instant.now();
        source = src;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public String
    getName()
    {
        return name;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public UUID
    getCorrelationId()
    {
        return correlationId;
    }

    /*************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    public Instant
    getTimestamp()
    {
        return timestamp;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    getSource()
    {
        return source;
    }
}

//////////////////////////////////////////////////////////////////////////////
