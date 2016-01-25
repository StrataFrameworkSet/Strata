//  ##########################################################################
//  # File Name: InMemoryUnitOfWork.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Persistence.Repository;
using Strata.Common.Utility;

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
    class InMemoryUnitOfWork:
        IUnitOfWork
    {
        public IDictionary<String,String> ErrorList { get; set; }

        private IDictionary<EntityIdentifier,Object> inserted;
        private IDictionary<EntityIdentifier,Object> updated;
        private IDictionary<EntityIdentifier,Object> removed;
        private UnitOfWorkState                      state;

        private IDictionary<EntityIdentifier,Object> entities;
        private IDictionary<String,IRepositoryMethod>     methods; 

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>InMemoryUnitOfWork</c> instance.
        /// </summary>
        /// 
        /// <param name="ent">in memory entities</param>
        /// 
        public 
        InMemoryUnitOfWork(
            IDictionary<EntityIdentifier,Object> ent,
            IDictionary<String,IRepositoryMethod>     meth)
        {
            ErrorList = new Dictionary<string, string>();
            inserted  = new Dictionary<EntityIdentifier,Object>();
            updated   = new Dictionary<EntityIdentifier,Object>();
            removed   = new Dictionary<EntityIdentifier,Object>();
            state     = UnitOfWorkState.ACTIVE;
            entities  = ent;
            methods   = meth;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.GetRepositoryMethod"/>
        /// 
        public IRepositoryMethod
        GetRepositoryMethod(String name)
        {
            return methods[name];
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.Commit()"/>
        /// 
        public void 
        Commit()
        {
            try
            {
                if ( !IsActive() )
                    throw new InvalidOperationException("inactive transaction");
            
                CommitInserted();
                CommitUpdated();
                CommitRemoved();

                inserted.Clear();
                updated.Clear();
                removed.Clear();            
            
                state = UnitOfWorkState.COMMITTED;
            }
            catch (Exception cause)
            {
                throw new CommitFailedException("commit failed",cause);
            }      
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.Rollback()"/>
        /// 
        public void 
        Rollback()
        {
            inserted.Clear();
            updated.Clear();
            removed.Clear();            
            state = UnitOfWorkState.ROLLED_BACK;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsActive()"/>
        /// 
        public bool 
        IsActive()
        {
            return state == UnitOfWorkState.ACTIVE;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsCommitted()"/>
        /// 
        public bool 
        IsCommitted()
        {
            return state == UnitOfWorkState.COMMITTED;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsRolledBack()"/>
        /// 
        public bool 
        IsRolledBack()
        {
            return state == UnitOfWorkState.ROLLED_BACK;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoInsert<K,T>(
            T                      entity,
            IEntityReplicator<K,T> replicator,
            KeyRetriever<K,T>      retriever)
            where T:class
        {
            T                insertedEntity = replicator.Replicate( entity );
            K                key = retriever( insertedEntity );
            EntityIdentifier id  = new EntityIdentifier(typeof(T),key);   
     
            if ( updated.ContainsKey( id ) )
                throw new InvalidOperationException("entity is already updated");
        
            if ( removed.ContainsKey( id ) )
                throw new InvalidOperationException("entity is already removed");
        
            if ( inserted.ContainsKey( id ) || DoHas<K,T>( key ) )
                throw new InvalidOperationException("entity is already inserted");
        
            inserted.Add( id,insertedEntity );
            return insertedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public  T 
        DoUpdate<K,T>(
            T                      entity,
            IEntityReplicator<K,T> replicator,
            KeyRetriever<K,T>      retriever)
            where T:class
        {
            T                updatedEntity = replicator.Replicate( entity );
            K                key = retriever( updatedEntity );
            EntityIdentifier id  = new EntityIdentifier(typeof(T),key);   
        
            if ( !inserted.ContainsKey( id ) && !DoHas<K,T>( key ) )
                throw new InvalidOperationException("entity does not exist");
        
            if ( removed.ContainsKey( id ) )
                throw new InvalidOperationException("entity is already removed");
        
            if ( updated.ContainsKey( id ) )
                updated[id] = updatedEntity;
            else
                updated.Add( id,updatedEntity );

            return updatedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        DoRemove<K,T>(T entity,KeyRetriever<K,T> retriever)
        {
            K                key = retriever( entity );
            EntityIdentifier id  = new EntityIdentifier( typeof(T),key );       
         
            if ( !inserted.ContainsKey( id ) && !DoHas<K,T>( key ) )
                throw new InvalidOperationException("entity does not exist");

            removed.Add( id,entity );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T
        DoGet<K,T>(K key)
            where T:class
        {
            EntityIdentifier id = new EntityIdentifier( typeof(T),key );

            if ( removed.ContainsKey( id ) )
                return null;

            if ( updated.ContainsKey( id ) )
                return (T)updated[id];

            if ( inserted.ContainsKey( id ) )
                return (T)inserted[id];

            return (T)entities[id];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IQueryable<T>
        DoQuery<T>()
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier, Object>(entities);

            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

             return
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable();
        }
    
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        DoHas<K,T>(K key)
        {
            EntityIdentifier id = new EntityIdentifier( typeof(T),key );

            if ( removed.ContainsKey( id ) )
                return false;

            if ( inserted.ContainsKey( id ) )
                return true;

            if ( updated.ContainsKey( id ) )
                return true;

            return entities.ContainsKey( id );
        }
    
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        CommitInserted()
        {
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry in inserted)
                entities.Add( entry.Key,entry.Value );
        }
    
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        CommitUpdated()
        {
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry in updated)
                entities[entry.Key] = entry.Value;
        }
   
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        CommitRemoved()
        {
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry in removed)
            {
                if ( entities.Remove( entry.Key ) == false )
                    throw 
                        new InvalidOperationException("entity does not exist");
            }
        }
    }
}

//  ##########################################################################
