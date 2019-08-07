//  ##########################################################################
//  # File Name: NhibernateNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate;
using NHibernate.Type;
using Strata.Foundation.Utility;
using Strata.Nhibernate.UnitOfWork;
using Strata.Domain.NamedQuery;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Collections;

namespace Strata.Nhibernate.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an NHibernate specific implementation of the 
    /// <c>IFinder{T}</c> interface based on NHibernate
    /// Named Queries.
    /// </summary>
    /// 
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class NhibernateNamedQuery<T>:
        AbstractNamedQuery<T>
        where T:class
    {
	    private NhibernateUnitOfWork unitOfWork;
	    private IList<T>	         result;
	    private IEnumerator<T>	     current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public 
        NhibernateNamedQuery(
            String               queryName,
            NhibernateUnitOfWork uow):
            base(queryName)
        {
            IQuery query;

            unitOfWork = uow;
            query = unitOfWork.DoGetNamedQuery(Name);

            if ( 
                !HasReturnType( query,typeof(T) ) && 
                !HasIntegerReturnType( query ) )
                throw 
                    new FinderCreationException( 
                        "Implementation query must return " + typeof(T) +
                        " or integer type (long, int, short)" );

		    result      = null;
		    current     = null;            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public 
        NhibernateNamedQuery(NhibernateNamedQuery<T> other):
            base( other )
        {
            IQuery query;

            unitOfWork = other.unitOfWork;
            query      = unitOfWork.DoGetNamedQuery(Name);

            if ( 
                !HasReturnType( query,typeof(T) ) && 
                !HasIntegerReturnType( query ) )
                throw 
                    new FinderCreationException( 
                        "Implementation query must return " + typeof(T) +
                        " or integer type (long, int, short)" );

		    result      = null;
		    current     = null;            
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override ICopyable 
        MakeCopy()
        {
            return new NhibernateNamedQuery<T>( this );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.Clear()"/>
        /// </summary>
        /// 
        public override void 
        Clear()
        {
		    ClearInputs();
		    result  = null;
		    current = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.Clear()"/>
        /// </summary>
        /// 
        public override void 
        Execute()
        {
		    EvaluateGet(ResultCardinality.ZERO_TO_MANY);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetAll()"/>
        /// </summary>
        /// 
        public override ICollection<T> 
        GetAll()
        {
		    EvaluateGet(ResultCardinality.ZERO_TO_MANY);
		    return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetUnique()"/>
        /// </summary>
        /// 
        public override T 
        GetUnique()
        {
		    EvaluateGet(ResultCardinality.ZERO_TO_ONE);

            return result.SingleOrDefault();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetNext()"/>
        /// </summary>
        /// 
        public override T 
        GetNext()
        {
		    EvaluateGet(ResultCardinality.ZERO_TO_MANY);

            try
            {
                T next = current.Current;
                
                current.MoveNext();
                return next;
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasUnique()"/>
        /// </summary>
        /// 
        public override bool 
        HasUnique()
        {
            return EvaluateHas() == 1;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasAny()"/>
        /// </summary>
        /// 
        public override bool 
        HasAny()
        {
            return EvaluateHas() > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasNext()"/>
        /// </summary>
        /// 
        public override bool 
        HasNext()
        {
		    EvaluateGet(ResultCardinality.ZERO_TO_MANY);

            try
            {
                T next = current.Current;
                
                return true;
            }
            catch (InvalidOperationException)
            {
                return false;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query, sets the results, and intializes 
        /// the enumerator.
        /// </summary>
        /// 
        /// <param name="cardinality">expected cardinality of results</param>
        /// 
	    protected void
	    EvaluateGet(ResultCardinality cardinality)
	    {
		    if ( result == null )
		    {
			    InputKeeper keeper = GetInputs();
			
			    switch ( keeper.Mode )
			    {
			    case InputMode.NAMED:
				    result = 
				        EvaluateGetWithNamedInputs(
                            unitOfWork, keeper, cardinality);
				    break;
				
			    case InputMode.POSITIONAL:
				    result = 
				        EvaluateGetWithPositionalInputs(
                            unitOfWork, keeper, cardinality);
				    break;
				
			    default:
				    result =
                        EvaluateGetWithNoInputs(unitOfWork, cardinality);
				    break;
			    }
			
			    current = result.GetEnumerator();
                current.MoveNext();
		    }
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query using named inputs.
        /// </summary>
        /// 
        /// <param name="unitOfWork">Nhibernate unit of work</param>
        /// <param name="keeper">input keeper</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <returns>query result</returns>
        /// 
        private IList<T> 
        EvaluateGetWithNamedInputs(
    	    NhibernateUnitOfWork            unitOfWork,
    	    InputKeeper                     keeper,
    	    ResultCardinality               cardinality)
        {
            IQuery                     query  = unitOfWork.DoGetNamedQuery(Name);
    	    IDictionary<String,Object> inputs = keeper.GetNamedInputs();

            foreach (KeyValuePair<String,Object> input in inputs)
                if (IsCollectionType(input.Value))
                    query.SetParameterList(input.Key,(IEnumerable)input.Value);
                else
    	            query.SetParameter( input.Key,input.Value );
    	
    	    return 
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? query.List<T>()
                    : new List<T>( new T[] { (T)query.UniqueResult()} );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query using positional inputs.
        /// </summary>
        /// 
        /// <param name="session">Nhibernate session</param>
        /// <param name="keeper">input keeper</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <returns>query result</returns>
        /// 
	    private IList<T> 
	    EvaluateGetWithPositionalInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper, 
		    ResultCardinality    cardinality)
	    {
            IQuery              query    = unitOfWork.DoGetNamedQuery(Name);
		    ICollection<Object> inputs   = keeper.GetPositionalInputs();
		    int                 position = 0;

            foreach (Object input in inputs)
		        query.SetParameter( position++,input );
		
		    return 
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? query.List<T>()
                    : new List<T>( new T[] { (T)query.UniqueResult()} );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query with no inputs.
        /// </summary>
        /// 
        /// <param name="session">Nhibernate session</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <returns>query result</returns>
        /// 
	    private IList<T> 
	    EvaluateGetWithNoInputs(
            NhibernateUnitOfWork unitOfWork,
            ResultCardinality    cardinality)
	    {
            IQuery query = unitOfWork.DoGetNamedQuery(Name);
	    
		    return
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? query.List<T>()
                    : new List<T>( new T[] { (T)query.UniqueResult()} );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query and returns the number of results.
        /// </summary>
        /// 
        /// <returns>the number of results</returns>
        /// 
        protected long
        EvaluateHas()
        {
			InputKeeper keeper  = GetInputs();
        
            switch ( keeper.Mode )
            {
            case InputMode.NAMED:
                return
                    EvaluateHasWithNamedInputs(unitOfWork, keeper);
            
            case InputMode.POSITIONAL:
                return
                    EvaluateHasWithPositionalInputs(unitOfWork, keeper);
            
            default:
                return
                    EvaluateHasWithNoInputs(unitOfWork);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private long 
        EvaluateHasWithNamedInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper)
        {
            IQuery                     query  = unitOfWork.DoGetNamedQuery(Name);
            IDictionary<String,Object> inputs = keeper.GetNamedInputs();

            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );

            foreach (KeyValuePair<String,Object> input in inputs)
                if (IsCollectionType(input.Value))
                    query.SetParameterList(input.Key,(IEnumerable)input.Value);
                else
                    query.SetParameter( input.Key,input.Value );
        
            return
                HasIntegerReturnType(query)
                    ? query.UniqueResult<long>()
                    : query.List().Count;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private long 
        EvaluateHasWithPositionalInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper keeper)
        {
            IQuery              query    = unitOfWork.DoGetNamedQuery(Name);
            ICollection<Object> inputs   = keeper.GetPositionalInputs();
            int                 position = 0;
        
            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );
        
            foreach (Object input in inputs)
                query.SetParameter( position++,input );
        
            return
                HasIntegerReturnType(query)
                    ? query.UniqueResult<long>()
                    : query.List().Count;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private long 
        EvaluateHasWithNoInputs(NhibernateUnitOfWork unitOfWork)
        {
            IQuery query = unitOfWork.DoGetNamedQuery(Name);
        
            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );
        
            return
                HasIntegerReturnType(query)
                    ? query.UniqueResult<long>()
                    : query.List().Count;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool 
        HasReturnType(IQuery query,Type type)
        {
            IType[] returnTypes = query.ReturnTypes;

            return 
                returnTypes.Count() == 1 &&
                returnTypes.First().ReturnedClass.IsAssignableFrom( type );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        HasIntegerReturnType(IQuery query)
        {
            IType[] returnTypes = query.ReturnTypes;

            return
                returnTypes.Count() == 1 && (
                returnTypes.First().Equals(NHibernateUtil.Int64) ||
                returnTypes.First().Equals(NHibernateUtil.Int32) ||
                returnTypes.First().Equals(NHibernateUtil.Int16));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool 
        IsCollectionType(object input)
        {
            return
                input
                    .GetType()
                    .GetInterfaces()
                    .Where(i => i.IsGenericType)
                    .Any(
                        i => i.GetGenericTypeDefinition() ==
                        typeof(ICollection<>));
        }

    }
}

//  ##########################################################################
