//  ##########################################################################
//  # File Name: EntityIdentifier.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.InMemoryRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class EntityIdentifier:
        IEquatable<EntityIdentifier>
    {
        public Type   EntityType { get; protected set; }
        public Object EntityKey { get; protected set; }

        public 
        EntityIdentifier(Type type,Object key)
        {
            EntityType = type;
            EntityKey  = key;
        }

        public bool 
        Equals(EntityIdentifier other)
        {
            return 
                EntityType == other.EntityType &&
                EntityKey.Equals( other.EntityKey );
        }

        public override bool
        Equals(Object other)
        {
            return 
                other is EntityIdentifier && 
                Equals( (EntityIdentifier)other );
        }

        public override int 
        GetHashCode()
        {
		    int hash = 7;
		
		    hash = 31 * hash + EntityType.GetHashCode();
		    hash = 31 * hash + EntityKey.GetHashCode();
		    return hash;
        }
    }
}

//  ##########################################################################
