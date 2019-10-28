//  ##########################################################################
//  # File Name: InMemoryUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using Strata.Domain.InMemory.NamedQuery;

namespace Strata.Domain.InMemory.UnitOfWOrk
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class InMemoryUnitOfWorkProvider:
        AbstractUnitOfWorkProvider
    {
        private readonly IDictionary<EntityIdentifier,object> entities;
	    private InMemoryUnitOfWork                            unitOfWork;
	    private readonly IDictionary<Type,IList<object>>      namedQueries;
        private readonly IDictionary<Type,object>             replicators;
        private readonly IDictionary<Type,IEntityReplicator<object>> 
                                                              objectReplicators;
        private readonly IDictionary<Type,object>             retrievers;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        InMemoryUnitOfWorkProvider()
        {
            entities     = new Dictionary<EntityIdentifier,object>();
            namedQueries = new Dictionary<Type,IList<object>>();
            replicators  = new Dictionary<Type,object>();
            objectReplicators = new Dictionary<Type,IEntityReplicator<object>>();
            retrievers   = new Dictionary<Type,object>();
            unitOfWork   = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IUnitOfWork 
        GetUnitOfWork()
        {
            if ( MustCreateNewUnitOfWork() )
                unitOfWork = new InMemoryUnitOfWork(this,entities);
        
            return unitOfWork;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        ClearUnitOfWork()
        {
            if ( unitOfWork != null ) 
                unitOfWork.Dispose();

            unitOfWork = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IList<T>
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
        public InMemoryUnitOfWorkProvider
        InsertNamedQuery<T>(InMemoryNamedQuery<T> namedQuery)
            where T:class
        {
            Type type = typeof(T);

            if ( !namedQueries.ContainsKey( type ) )
                namedQueries.Add( type,new List<Object>() );
        
            if ( 
                !namedQueries[type].Any( 
                    f => ((IMethod)f).Name.Equals( namedQuery.Name ) ) )
                namedQueries[type].Add( namedQuery );

            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public InMemoryUnitOfWorkProvider
        InsertReplicator<T>(IEntityReplicator<T> replicator)
            where T:class
        {
            replicators.Add(typeof(T),replicator);
            objectReplicators.Add(typeof(T),new ObjectReplicator<T>(replicator));
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public InMemoryUnitOfWorkProvider
        InsertRetriever<K,T>(KeyRetriever<K,T> retriever)
            where T:class
        {
            retrievers.Add(typeof(T),retriever);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public INamedQuery<T>
        GetNamedQuery<T>(string queryName) 
            where T: class
        {
            return
                GetNamedQueries<T>()
                    .FirstOrDefault(q => q.Name.Equals(queryName));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IEntityReplicator<T>
        GetReplicator<T>()
            where T : class
        {
            return (IEntityReplicator<T>)replicators[typeof(T)];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IEntityReplicator<object>
        GetObjectReplicator(Type type) 
        {
            return objectReplicators[type];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public KeyRetriever<K,T>
        GetRetriever<K,T>() 
            where T: class
        {
            return (KeyRetriever<K,T>)retrievers[typeof(T)];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected IList<InMemoryNamedQuery<T>>
        GetNamedQueries<T>()
            where T:class
        {
            Type                         type = typeof(T);
            IList<InMemoryNamedQuery<T>> output = 
                new List<InMemoryNamedQuery<T>>();
        
            if ( namedQueries.ContainsKey( type ) )
                foreach (Object finder in namedQueries[type])
                    output.Add( (InMemoryNamedQuery<T>)finder );
        
            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        HasNamedQuery<T>(string queryName) 
            where T: class
        {
            return
                GetNamedQueries<T>()
                    .Any(q => q.Name.Equals(queryName));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        MustCreateNewUnitOfWork()
        {
            return 
                unitOfWork == null ||
                !unitOfWork.IsActive();    
        }

    }
}

//  ##########################################################################
