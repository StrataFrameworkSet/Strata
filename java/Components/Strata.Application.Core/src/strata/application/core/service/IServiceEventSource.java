//////////////////////////////////////////////////////////////////////////////
// IServiceEventSource.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import java.util.Set;

public
interface IServiceEventSource<
    S extends IServiceEventSource<S,E,O>,
    E extends IServiceEvent<S>,
    O extends IServiceEventObserver<E>>
{
    /*************************************************************************
     * Attaches the observers from another {@code IServiceEventSource<S,E,O>}
     * to this {@code IServiceEventSource<S,E,O>}.
     *
     * @param  other provides observers
     * @return this for method chaining
     */
    S
    attachFrom(S other);

    /*************************************************************************
     * Attaches a set of observers to this {@code IServiceEventSource<S,E,O>}.
     *
     * @param  observers set of observers being attached
     * @return this for method chaining
     */
    S
    attach(Set<O> observers);

    /*************************************************************************
     * Attaches an observer to this {@code IServiceEventSource<S,E,O>}.
     *
     * @param  observer observer being attached
     * @return this for method chaining
     */
    S
    attach(O observer);

    /*************************************************************************
     * Detaches an observer from this {@code IServiceEventSource<S,E,O>}.
     *
     * @param  observer observer being detached
     * @return this for method chaining
     */
    S
    detach(O observer);

    /************************************************************************
     * Returns the observers of this {@code IServiceEventSource<S,E,O>}.
     *
     * @return the set of currently attached observers
     */
    Set<O>
    getObservers();

    /************************************************************************
     * Determines if an observer is attached to this
     * {@code IServiceEventSource<S,E,O>}.
     *
     * @param  observer target of query
     * @return true if observer is attached
     *         to this {@code IServiceEventSource<S,E,O>}
     */
    boolean
    has(O observer);

    /************************************************************************
     * Notifies all currently attached observers of the specified event.
     *
     * @param  event event to be sent to observers
     * @return this for method chaining
     */
    S
    notify(E event);
}

//////////////////////////////////////////////////////////////////////////////