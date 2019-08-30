//////////////////////////////////////////////////////////////////////////////
// AbstractServiceEvent.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import java.time.LocalDateTime;

/*****************************************************************************
 * Base implementation of {@code IServiceEvent<S>} interface.
 *
 * @param <S> event source type
 */
public abstract
class AbstractServiceEvent<S>
    implements IServiceEvent<S>
{
    private final String name;
    private final LocalDateTime timestamp;
    private final S             source;

    /*************************************************************************
     * Creates a new instance of {@code AbstractServiceEvent<S>}.
     *
     * @param nm  event name
     * @param src event source
     */
    protected
    AbstractServiceEvent(String nm,S src)
    {
        name = nm;
        timestamp = LocalDateTime.now();
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
    public LocalDateTime
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
