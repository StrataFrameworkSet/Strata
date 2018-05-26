//  ##########################################################################
//  # File Name: IEntityReplicator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.InMemoryRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Interface for replicating entity instances.
    /// </summary>
    /// 
    /// <typeparam name="K">Key Type</typeparam>
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IEntityReplicator<K,T>
        where T:class
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Replicates the entity by:
        /// <list type="bullet">
        /// <item>making a copy of the entity</item>
        /// <item>determining if the entity has an unassigned key</item>
        /// <item>generate and assign key to copy if key unassigned </item>
        /// </list>
        /// </summary>
        /// 
        /// <param name="entity">entity to be replicated</param>
        /// <returns>replicated entity</returns>
        /// 
        T 
        Replicate(T entity);
    }
}

//  ##########################################################################
