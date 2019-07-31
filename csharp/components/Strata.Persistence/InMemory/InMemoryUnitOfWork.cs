//  ##########################################################################
//  # File Name: InMemoryUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Reflection;
using Strata.Foundation.Utility;
using Strata.Persistence.NamedQuery;
using Strata.Persistence.UnitOfWork;

namespace Strata.Persistence.InMemory
{

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class InMemoryUnitOfWork:
        AbstractUnitOfWork
    {
        public bool IsDisposed { get; protected set; }

        private readonly IDictionary<EntityIdentifier,Object>  inserted;
        private readonly IDictionary<EntityIdentifier,Object>  updated;
        private readonly IDictionary<EntityIdentifier,Object>  removed;
        private readonly IDictionary<EntityIdentifier,Object>  entities;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>InMemoryUnitOfWork</c> instance.
        /// </summary>
        /// 
        /// <param name="ent">in memory entities</param>
        /// 
        public 
        InMemoryUnitOfWork(
            InMemoryUnitOfWorkProvider           provider,
            IDictionary<EntityIdentifier,Object> ent):
            base(provider)
        {
            inserted   = new Dictionary<EntityIdentifier,Object>();
            updated    = new Dictionary<EntityIdentifier,Object>();
            removed    = new Dictionary<EntityIdentifier,Object>();
            entities   = ent;
            IsDisposed = false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        Dispose()
        {
            inserted.Clear();
            updated.Clear();
            removed.Clear();
            IsDisposed = true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoInsert<K,T>(T entity)
        {
            T                insertedEntity = null;
            K                key            = default(K);
            EntityIdentifier id             = null;
        
            insertedEntity = GetReplicator<T>().Replicate(entity);
            key            = GetRetriever<K,T>()(insertedEntity);
            id             = new EntityIdentifier(typeof(T),key);
        
            if ( updated.ContainsKey( id ) )
                throw new ArgumentException("entity is already updated");
        
            if ( removed.ContainsKey( id ) )
                throw new ArgumentException("entity is already removed");
        
            if ( inserted.ContainsKey(id) || DoHasUniqueWithKey<K,T>(key))
                throw new ArgumentException("entity is already inserted");
        
            inserted.Add( id,insertedEntity );
            return insertedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoUpdate<K,T>(T entity)
        {
            T                updatedEntity = null;
            K                key           = default(K);
            EntityIdentifier id            = null;
        
            updatedEntity = GetReplicator<T>().Replicate(entity);
            key =  GetRetriever<K,T>()(updatedEntity);
            id = new EntityIdentifier(typeof(T),key);
        
            if ( removed.ContainsKey( id ) )
                throw new ArgumentException("entity is already removed");

            if ( !inserted.ContainsKey(id) && !DoHasUniqueWithKey<K,T>(key))
                throw new ArgumentException("entity does not exist");
        
            updated.Add( id,updatedEntity );
            return updatedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRemove<K,T>(T entity)
        {
            K                key = default(K);
            EntityIdentifier id  = null;
        
            key = GetRetriever<K,T>()(entity);
            id = new EntityIdentifier(typeof(T),key);

            if ( !inserted.ContainsKey(id) && !DoHasUniqueWithKey<K,T>(key))
                throw new ArgumentException("entity does not exist");
        
            removed.Add( id,entity );        
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithKey<K,T>(K key)
        {
            EntityIdentifier id = new EntityIdentifier(typeof(T),key);
        
            if ( removed.ContainsKey( id ) )
                return null;
 
            if ( updated.ContainsKey( id ) )
                return (T)updated[id];

            if ( inserted.ContainsKey( id ) )
                return (T)inserted[id];
        
            return GetReplicator<T>().Replicate(GetCandidates<K,T>()[key]);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            T result =
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .SingleOrDefault();

            if (result != null)
                return GetReplicator<T>().Replicate(result);

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            return
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .SingleOrDefault();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAllWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            IList<T> temp =
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .ToList();
            IList<T> results = new List<T>();

            if (temp != null && temp.Count > 0)
                foreach (T entity in temp)
                    results.Add(GetReplicator<T>().Replicate(entity));

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            results.AddAll(
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .ToList());

            return results;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAll<T>()
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            IList<T> temp =
                queryable
                    .Values
                    .OfType<T>()
                    .ToList();
            IList<T> results = new List<T>();

            if (temp != null && temp.Count > 0)
                foreach (T entity in temp)
                    results.Add(GetReplicator<T>().Replicate(entity));

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            results.AddAll(
                queryable
                    .Values
                    .OfType<T>()
                    .ToList());

            return results;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override INamedQuery<T> 
        DoGetNamedQuery<T>(string queryName)
        {
            return
                ((InMemoryUnitOfWorkProvider) Provider)
                    .GetNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasUniqueWithKey<K,T>(K key)
        {
            EntityIdentifier id = new EntityIdentifier(typeof(T),key);
        
            if ( removed.ContainsKey( id ) )
                return false;
 
            if ( updated.ContainsKey( id ) )
                return true;

            if ( inserted.ContainsKey( id ) )
                return true;
        
            return GetCandidates<K,T>().ContainsKey( key );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasAnyWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);

            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            return
                queryable
                    .Values
                    .OfType<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .Any();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasNamedQuery<T>(string queryName)
        {
            return ((InMemoryUnitOfWorkProvider) Provider).HasNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoCommit()
        {
            try
            {
                CommitInserted();
                CommitUpdated();
                CommitRemoved();

                inserted.Clear();
                updated.Clear();
                removed.Clear();            
            }
            catch (Exception cause)
            {
                throw new CommitFailedException("commit failed",cause);
            }      
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRollback()
        {
            try
            {
                inserted.Clear();
                updated.Clear();
                removed.Clear();
                base.DoRollback();
            }
            catch (RollbackFailedException)
            {
                throw;
            }
            catch (Exception cause)
            {
                throw new RollbackFailedException("rollback failed",cause);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoInsert<K,T>(
            T                    entity,
            IEntityReplicator<T> replicator,
            KeyRetriever<K,T>    retriever)
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
            T                    entity,
            IEntityReplicator<T> replicator,
            KeyRetriever<K,T>    retriever)
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
        public void
        CommitInserted()
        {
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry in inserted)
                entities.Add( entry.Key,Replicate(entry.Value) );
        }
    
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void
        CommitUpdated()
        {
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry in updated)
                entities[entry.Key] = Replicate(entry.Value);
        }
   
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void
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

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IEntityReplicator<T>
        GetReplicator<T>()
            where T: class
        {
            return 
                ((InMemoryUnitOfWorkProvider)Provider).GetReplicator<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public KeyRetriever<K,T>
        GetRetriever<K,T>()
            where T: class
        {
            return 
                ((InMemoryUnitOfWorkProvider)Provider).GetRetriever<K,T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IDictionary<K,T>
        GetCandidates<K,T>()
            where T: class
        {
            IDictionary<K,T> candidates = new Dictionary<K,T>();
            KeyRetriever<K,T> retriever = GetRetriever<K,T>();

            foreach (T entity in GetEntitiesByType<T>())
                candidates.Add(retriever(entity),entity);
                
            return candidates;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IList<T>
        GetEntitiesByType<T>()
        {
            Type     type   = typeof(T);
            IList<T> output = new List<T>();
        
            foreach (
                KeyValuePair<EntityIdentifier,Object> entry 
                    in entities)
            {
                if ( type.IsAssignableFrom( entry.Key.EntityType ) )
                    output.Add( (T)( entry.Value ) );
            }
        
            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private object
        Replicate(object source)
        {
            IEntityReplicator<object> replicator = 
                ((InMemoryUnitOfWorkProvider)Provider).GetObjectReplicator(
                    source.GetType());

            return replicator.Replicate(source);
        }
    }
}

//  ##########################################################################
