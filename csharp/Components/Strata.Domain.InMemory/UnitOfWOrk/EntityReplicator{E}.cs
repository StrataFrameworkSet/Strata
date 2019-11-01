//  ##########################################################################
//  # File Name: EntityReplicator{E}.cs
//  ##########################################################################

using System;

namespace Strata.Domain.InMemory.UnitOfWOrk
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class EntityReplicator<E>:
        IEntityReplicator<E>
        where E: class
    {
        private readonly Func<E,E> itsReplicator;

        public 
        EntityReplicator(Func<E,E> replicator)
        {
            itsReplicator = replicator;
        }

        public E 
        Replicate(E entity)
        {
            return itsReplicator.Invoke(entity);
        }
    }
}

//  ##########################################################################
