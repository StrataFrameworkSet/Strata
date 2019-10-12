//////////////////////////////////////////////////////////////////////////////
// IReadOnlyUnitOfWork.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.unitofwork;

import java.util.Collection;
import java.util.Set;

public
interface IReadOnlyUnitOfWork
{
    <E> Collection<E>
    getEntitiesByType(Class<E> entityType);

    <K,E> Collection<E>
    getEntitiesIn(
        Class<E>              entityType,
        Set<K>                keys,
        IMembershipCheck<K,E> predicate);
}

//////////////////////////////////////////////////////////////////////////////