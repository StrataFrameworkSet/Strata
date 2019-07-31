//  ##########################################################################
//  # File Name: AbstractEntityReplicator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.InMemory
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Abstract base for all <c>IEntityReplicator{K,T}</c> types.
    /// Implements the <c>IEntityReplicator{K,T}.Replicate({T})</c>
    /// method with the 
    /// <see cref="!http://www.dofactory.com/Patterns/PatternTemplate.aspx">
    /// Template Method</see> pattern.
    /// </summary>
    /// 
    /// <typeparam name="K">Key Type</typeparam>
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractEntityReplicator<K,T>:
        IEntityReplicator<T>
        where T:class
    {
        //////////////////////////////////////////////////////////////////////
        /// <see cref="IEntityReplicator{K,T}.Replicate({T})"/>
        /// 
        public T 
        Replicate(T entity)
        {
            T copy = MakeCopy(entity);

            if ( HasUnassignedKey(entity) )
                AssignKey(copy,GenerateKey());

            return copy;    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Makes a copy of the entity. Subclasses must implement this
        /// method to create a copy of the original in a manner appropriate
        /// for the actual type of T.
        /// </summary>
        /// 
        /// <param name="original">original entity</param>
        /// <returns>copy of original enity</returns>
        /// 
        /// <postcondition>
        /// result.Equals(original)
        /// </postcondition>
        /// 
        protected abstract T 
        MakeCopy(T original);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified entity has an assigned key.
        /// Subclasses must implement this method specifically for
        /// each actual type T.
        /// </summary>
        /// 
        /// <param name="entity">
        /// entity to determine if key is assigned
        /// </param>
        /// <returns>true if entity's key is unassigned</returns>
        /// 
        protected abstract bool 
        HasUnassignedKey(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Assigns a key to the provided entity. Subclasses must implement
        /// this method specifically for each actual type T with key type K.
        /// </summary>
        /// 
        /// <param name="entity">entity to have key assigned</param>
        /// <param name="key">key to be assigned</param>
        /// 
        /// <postcondition>
        /// this.HasUnassignedKey(entity) == false
        /// </postcondition>
        /// 
        protected abstract void 
        AssignKey(T entity,K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Generates a new unique key. Subclasses must implement
        /// this method specifically for each actual type K.
        /// </summary>
        /// 
        /// <returns>new unique key</returns>
        /// 
        protected abstract K 
        GenerateKey();
    }
}

//  ##########################################################################
