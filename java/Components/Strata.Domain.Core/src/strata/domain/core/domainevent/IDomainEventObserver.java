//////////////////////////////////////////////////////////////////////////////
// IDomainEventObserver.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.domainevent;

public
interface IDomainEventObserver<E>
{
    void
    onEvent(E event);

    void
    onException(Exception e);
}

//////////////////////////////////////////////////////////////////////////////