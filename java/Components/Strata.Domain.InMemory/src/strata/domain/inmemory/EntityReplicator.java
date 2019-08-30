//////////////////////////////////////////////////////////////////////////////
// EntityReplicator.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.inmemory;

import java.util.function.Predicate;

public
class EntityReplicator<K,E>
    implements IEntityReplicator<K,E>
{
    private final ICopier<E>         itsCopier;
    private final Predicate<E> itsKeyCheck;
    private final IKeyCreator<E> itsKeyCreator;

    public
    EntityReplicator(
        ICopier<E>     copier,
        Predicate<E> keyCheck,
        IKeyCreator<E> keyCreator)
    {
        itsCopier = copier;
        itsKeyCheck = keyCheck;
        itsKeyCreator = keyCreator;
    }

    @Override
    public E
    replicate(E entity)
    {
        E replicant = itsCopier.copy(entity);

        if (itsKeyCheck.test(replicant))
            replicant = itsKeyCreator.createAndSetKey(replicant);

        return replicant;
    }
}

//////////////////////////////////////////////////////////////////////////////
