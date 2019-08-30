//////////////////////////////////////////////////////////////////////////////
// AbstractServiceEventSource.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import java.util.HashSet;
import java.util.Set;

/*****************************************************************************
 * Base implementation of the {@code IServiceEventSource<S,E,O>} interface.
 *
 * @param <S> service event source type
 * @param <E> service event type
 * @param <O> service event observer type
 */
public abstract
class AbstractServiceEventSource<
    S extends IServiceEventSource<S,E,O>,
    E extends IServiceEvent<S>,
    O extends IServiceEventObserver<E>>
    implements IServiceEventSource<S,E,O>
{
    private final Set<O> observers;

    /*************************************************************************
     * Creates  a new instance of {@code AbstractServiceEventSource<S,E,O>}.
     */
    protected
    AbstractServiceEventSource()
    {
        observers = new HashSet<O>();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    attachFrom(S other)
    {
        observers.addAll(other.getObservers());
        return getSelf();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    attach(Set<O> observers)
    {
        observers.addAll(observers);
        return getSelf();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    attach(O observer)
    {
        observers.add(observer);
        return getSelf();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    detach(O observer)
    {
        observers.remove(observer);
        return getSelf();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public boolean
    has(O observer)
    {
        return observers.contains(observer);
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public S
    notify(E event)
    {
        for (O observer:observers)
            observer.onEvent(event);

        return getSelf();
    }

    /*************************************************************************
     * Returns a reference to this.
     *
     * @return reference to this for method chaining
     */
    protected abstract S
    getSelf();
}

//////////////////////////////////////////////////////////////////////////////
