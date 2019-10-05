//////////////////////////////////////////////////////////////////////////////
// AbstractDomainEvent.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.domainevent;

import java.time.Instant;

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
        name = nm;
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
