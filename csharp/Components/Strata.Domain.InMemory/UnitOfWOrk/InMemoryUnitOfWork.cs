//  ##########################################################################
//  # File Name: InMemoryUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.InMemory.UnitOfWOrk
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
        public override async Task<E> 
        DoInsert<K,E>(E entity)
        {
            E                insertedEntity = null;
            K                key            = default(K);
            EntityIdentifier id             = null;
        
            insertedEntity = GetReplicator<E>().Replicate(entity);
            key            = GetRetriever<K,E>()(insertedEntity);
            id             = new EntityIdentifier(typeof(E),key);
        
            if ( updated.ContainsKey( id ) )
                throw new ArgumentException("entity is already updated");
        
            if ( removed.ContainsKey( id ) )
                throw new ArgumentException("entity is already removed");
        
            if ( inserted.ContainsKey(id) || await DoHasUnique<K,E>(key))
                throw new ArgumentException("entity is already inserted");
        
            inserted.Add( id,insertedEntity );
            return insertedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<E> 
        DoUpdate<K,E>(E entity)
        {
            E                updatedEntity = null;
            K                key           = default(K);
            EntityIdentifier id            = null;
            bool             hasEntity     = false;
        
            updatedEntity = GetReplicator<E>().Replicate(entity);
            key =  GetRetriever<K,E>()(updatedEntity);
            id = new EntityIdentifier(typeof(E),key);
        
            if ( removed.ContainsKey( id ) )
                throw new ArgumentException("entity is already removed");

            hasEntity = await DoHasUnique<K,E>(key);

            if ( !inserted.ContainsKey(id) && !hasEntity)
                throw new ArgumentException("entity does not exist");
        
            updated.Add( id,updatedEntity );
            return updatedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task
        DoRemove<K,E>(E entity)
        {
            K                key = default(K);
            EntityIdentifier id  = null;
        
            key = GetRetriever<K,E>()(entity);
            id = new EntityIdentifier(typeof(E),key);

            if ( !inserted.ContainsKey(id) && !await DoHasUnique<K,E>(key))
                throw new ArgumentException("entity does not exist");
        
            removed.Add( id,entity ); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<E>> 
        DoGetUnique<K,E>(K key)
        {
            EntityIdentifier id = new EntityIdentifier(typeof(E),key);
        
            if ( removed.ContainsKey( id ) )
                return Task.FromResult(Optional<E>.Empty());
 
            if ( updated.ContainsKey( id ) )
                return 
                    Task.FromResult(
                        Optional<E>.OfNullable((E)updated[id]));

            if ( inserted.ContainsKey( id ) )
                return 
                    Task.FromResult(
                        Optional<E>.OfNullable((E)inserted[id]));
        
            return 
                Task.FromResult(
                    Optional<E>.OfNullable(
                        GetReplicator<E>()
                            .Replicate(GetCandidates<K,E>()[key])));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<E>> 
        DoGetUniqueMatching<E>(Expression<Func<E,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            E result =
                queryable
                    .Values
                    .OfType<E>()
                    .AsQueryable()
                    .Where(predicate)
                    .SingleOrDefault();

            if (result != null)
                return 
                    Task.FromResult(
                        Optional<E>.OfNullable(
                            GetReplicator<E>().Replicate(result)));

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            return
                Task.FromResult(
                    Optional<E>.OfNullable(
                        queryable
                            .Values
                            .OfType<E>()
                            .AsQueryable()
                            .Where(predicate)
                            .SingleOrDefault()));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<IList<E>> 
        DoGetMatching<E>(Expression<Func<E,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            IList<E> temp =
                queryable
                    .Values
                    .OfType<E>()
                    .AsQueryable()
                    .Where(predicate)
                    .ToList();
            IList<E> results = new List<E>();

            if (temp != null && temp.Count > 0)
                foreach (E entity in temp)
                    results.Add(GetReplicator<E>().Replicate(entity));

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            results.AddAll(
                queryable
                    .Values
                    .OfType<E>()
                    .AsQueryable()
                    .Where(predicate)
                    .ToList());

            return Task.FromResult(results);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<IList<E>> 
        DoGetAll<E>()
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);
            IList<E> temp =
                queryable
                    .Values
                    .OfType<E>()
                    .ToList();
            IList<E> results = new List<E>();

            if (temp != null && temp.Count > 0)
                foreach (E entity in temp)
                    results.Add(GetReplicator<E>().Replicate(entity));

            queryable = new Dictionary<EntityIdentifier,object>();
            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            results.AddAll(
                queryable
                    .Values
                    .OfType<E>()
                    .ToList());

            return Task.FromResult(results);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<INamedQuery<E>> 
        DoGetNamedQuery<E>(string queryName)
        {
            return
                Task.FromResult(
                    ((InMemoryUnitOfWorkProvider) Provider)
                        .GetNamedQuery<E>(queryName));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        DoHasUnique<K,E>(K key)
        {
            EntityIdentifier id = new EntityIdentifier(typeof(E),key);
        
            if ( removed.ContainsKey( id ) )
                return Task.FromResult(false);
 
            if ( updated.ContainsKey( id ) )
                return Task.FromResult(true);

            if ( inserted.ContainsKey( id ) )
                return Task.FromResult(true);
        
            return Task.FromResult(GetCandidates<K,E>().ContainsKey(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        DoHasMatching<E>(Expression<Func<E,bool>> predicate)
        {
            IDictionary<EntityIdentifier,Object> queryable = 
                new Dictionary<EntityIdentifier,Object>(entities);

            queryable = queryable.Merge( inserted );
            queryable = queryable.Merge( updated );
            queryable = queryable.Filter( removed );

            return
                Task.FromResult(
                    queryable
                        .Values
                        .OfType<E>()
                        .AsQueryable()
                        .Where(predicate)
                        .Any());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        DoHasNamedQuery<E>(string queryName)
        {
            return 
                Task.FromResult(
                    ((InMemoryUnitOfWorkProvider)Provider)
                        .HasNamedQuery<E>(queryName));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task 
        DoCommit()
        {
            Task task =
                new Task(
                    () =>
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
                    });
            
            task.Start();
            return task;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task 
        DoRollback()
        {
            try
            {
                inserted.Clear();
                updated.Clear();
                removed.Clear();
                await base.DoRollback();
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
        public E 
        DoInsert<K,E>(
            E                    entity,
            IEntityReplicator<E> replicator,
            KeyRetriever<K,E>    retriever)
            where E:class
        {
            E                insertedEntity = replicator.Replicate( entity );
            K                key = retriever( insertedEntity );
            EntityIdentifier id  = new EntityIdentifier(typeof(E),key);   
     
            if ( updated.ContainsKey( id ) )
                throw new InvalidOperationException("entity is already updated");
        
            if ( removed.ContainsKey( id ) )
                throw new InvalidOperationException("entity is already removed");
        
            if ( inserted.ContainsKey( id ) || DoHas<K,E>( key ) )
                throw new InvalidOperationException("entity is already inserted");
        
            inserted.Add( id,insertedEntity );
            return insertedEntity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public  E 
        DoUpdate<K,E>(
            E                    entity,
            IEntityReplicator<E> replicator,
            KeyRetriever<K,E>    retriever)
            where E:class
        {
            E                updatedEntity = replicator.Replicate( entity );
            K                key = retriever( updatedEntity );
            EntityIdentifier id  = new EntityIdentifier(typeof(E),key);   
        
            if ( !inserted.ContainsKey( id ) && !DoHas<K,E>( key ) )
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
