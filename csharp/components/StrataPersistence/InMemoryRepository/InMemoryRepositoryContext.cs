//  ##########################################################################
//  # File Name: InMemoryRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Persistence.Repository;

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
    class InMemoryRepositoryContext:
        AbstractRepositoryContext
    {
        private IDictionary<EntityIdentifier,Object>  entities;
        private IDictionary<String,IRepositoryMethod> methods; 
	    private IDictionary<Type,IList<Object>>       finders;
	    private InMemoryUnitOfWork                    unitOfWork;

        public 
        InMemoryRepositoryContext()
        {
            entities     = new Dictionary<EntityIdentifier,Object>();
            methods      = new Dictionary<String,IRepositoryMethod>();
            finders      = new Dictionary<Type,IList<Object>>();
            unitOfWork   = null;
        }

        public override IUnitOfWork 
        GetUnitOfWork()
        {
            if ( unitOfWork == null || !unitOfWork.IsActive() )
                unitOfWork = new InMemoryUnitOfWork(entities,methods);
        
            return unitOfWork;
        }

        /************************************************************************
         *  
         *
         * @param type
         * @return
         */
        public IList<T>
        GetEntitiesByType<T>()
        {
            Type     type     = typeof(T);
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
    
        public void
        InsertNamedMethod(InMemoryRepositoryMethod method)
        {
            if ( !methods.ContainsKey( method.Name ) )
                methods.Add( method.Name,method );
        }

        /************************************************************************
         *  
         *
         * @param type
         * @param finder
         */
        public void
        InsertFinder<T>(InMemoryFinder<T> finder)
            where T:class
        {
            Type type = typeof(T);

            if ( !finders.ContainsKey( type ) )
                finders.Add( type,new List<Object>() );
        
            if ( 
                !finders[type].Any( 
                    f => ((IRepositoryMethod)f).Name.Equals( finder.Name ) ) )
                finders[type].Add( finder );
        }
    
        /************************************************************************
         *  
         *
         * @param type
         * @return
         */
        public IList<InMemoryFinder<T>>
        GetFinders<T>()
            where T:class
        {
            Type                     type = typeof(T);
            IList<InMemoryFinder<T>> output = 
                new List<InMemoryFinder<T>>();
        
            if ( finders.ContainsKey( type ) )
                foreach (Object finder in finders[type])
                    output.Add( (InMemoryFinder<T>)finder );
        
            return output;
        }

    }
}

//  ##########################################################################
