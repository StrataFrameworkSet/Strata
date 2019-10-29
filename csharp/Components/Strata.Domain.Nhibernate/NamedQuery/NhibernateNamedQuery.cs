//  ##########################################################################
//  # File Name: NhibernateNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using NHibernate;
using NHibernate.Type;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Nhibernate.UnitOfWork;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Nhibernate.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an NHibernate specific implementation of the 
    /// <c>IFinder{T}</c> interface based on NHibernate
    /// Named Queries.
    /// </summary>
    /// 
    /// <typeparam name="E">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class NhibernateNamedQuery<E>:
        AbstractNamedQuery<E>
        where E:class
    {
	    private NhibernateUnitOfWork unitOfWork;
	    private IList<E>	         result;
	    private IEnumerator<E>	     current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public 
        NhibernateNamedQuery(
            string               queryName,
            NhibernateUnitOfWork uow):
            base(queryName)
        {
            IQuery query;

            unitOfWork = uow;
            query = unitOfWork.DoGetNamedQuery(Name);

            if ( 
                !HasReturnType( query,typeof(E) ) && 
                !HasIntegerReturnType( query ) )
                throw 
                    new FinderCreationException( 
                        "Implementation query must return " + typeof(E) +
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
        NhibernateNamedQuery(NhibernateNamedQuery<E> other):
            base( other )
        {
            IQuery query;

            unitOfWork = other.unitOfWork;
            query      = unitOfWork.DoGetNamedQuery(Name);

            if ( 
                !HasReturnType( query,typeof(E) ) && 
                !HasIntegerReturnType( query ) )
                throw 
                    new FinderCreationException( 
                        "Implementation query must return " + typeof(E) +
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
            return new NhibernateNamedQuery<E>( this );
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
        public override async Task 
        Execute()
        {
		    await EvaluateGet(ResultCardinality.ZERO_TO_MANY);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetAll()"/>
        /// </summary>
        /// 
        public override async Task<ICollection<E>> 
        GetAll()
        {
		    await EvaluateGet(ResultCardinality.ZERO_TO_MANY);
		    return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetUnique()"/>
        /// </summary>
        /// 
        public override async Task<Optional<E>> 
        GetUnique()
        {
		    await EvaluateGet(ResultCardinality.ZERO_TO_ONE);

            return Optional<E>.OfNullable(result.SingleOrDefault());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetNext()"/>
        /// </summary>
        /// 
        public override async Task<Optional<E>> 
        GetNext()
        {
		    await EvaluateGet(ResultCardinality.ZERO_TO_MANY);

            try
            {
                E next = current.Current;
                
                current.MoveNext();
                return Optional<E>.OfNullable(next);
            }
            catch (InvalidOperationException)
            {
                return Optional<E>.Empty();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasUnique()"/>
        /// </summary>
        /// 
        public override async Task<bool> 
        HasUnique()
        {
            return (await EvaluateHas()) == 1;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasAny()"/>
        /// </summary>
        /// 
        public override async Task<bool> 
        HasAny()
        {
            return (await EvaluateHas()) > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasNext()"/>
        /// </summary>
        /// 
        public override async Task<bool> 
        HasNext()
        {
		    await EvaluateGet(ResultCardinality.ZERO_TO_MANY);

            try
            {
                E next = current.Current;
                
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
        /// <param name="cardinality">expected cardinality of results</param>
        /// 
        protected async Task
        EvaluateGet(ResultCardinality cardinality)
	    {
		    if ( result == null )
		    {
			    InputKeeper keeper = GetInputs();
			
			    switch ( keeper.Mode )
			    {
			    case InputMode.NAMED:
				    result =
                        await
				            EvaluateGetWithNamedInputs(
                                unitOfWork, keeper, cardinality);
				    break;
				
			    case InputMode.POSITIONAL:
				    result = 
                        await
				            EvaluateGetWithPositionalInputs(
                                unitOfWork, keeper, cardinality);
				    break;
				
			    default:
				    result =
                        await
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
        /// <param name="unitOfWork">Nhibernate unit of work</param>
        /// <param name="keeper">input keeper</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <returns>query result</returns>
        /// 
        private async Task<IList<E>> 
        EvaluateGetWithNamedInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper,
            ResultCardinality    cardinality)
        {
            IQuery                     query  = unitOfWork.DoGetNamedQuery(Name);
    	    IDictionary<String,Object> inputs = keeper.GetNamedInputs();
            IList<E>                   output = null;

            foreach (KeyValuePair<String,Object> input in inputs)
                if (IsCollectionType(input.Value))
                    query.SetParameterList(input.Key,(IEnumerable)input.Value);
                else
    	            query.SetParameter( input.Key,input.Value );
    	
    	    output = 
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? await 
                        query
                            .ListAsync<E>()
                            .ConfigureAwait(false)
                    : new List<E>( 
                        new E[]
                        {
                            await 
                                query
                                    .UniqueResultAsync<E>()
                                    .ConfigureAwait(false)
                        } );

            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query using positional inputs.
        /// </summary>
        /// <param name="unitOfWork"></param>
        /// <param name="keeper">input keeper</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <param name="session">Nhibernate session</param>
        /// <returns>query result</returns>
        /// 
        private async Task<IList<E>> 
        EvaluateGetWithPositionalInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper,
            ResultCardinality    cardinality)
	    {
            IQuery              query    = unitOfWork.DoGetNamedQuery(Name);
		    ICollection<Object> inputs   = keeper.GetPositionalInputs();
		    int                 position = 0;
            IList<E>            output = null;

            foreach (Object input in inputs)
		        query.SetParameter( position++,input );
		
		    output = 
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? await 
                        query
                            .ListAsync<E>()
                            .ConfigureAwait(false)
                    : new List<E>( 
                        new E[]
                        {
                            await 
                                query
                                    .UniqueResultAsync<E>()
                                    .ConfigureAwait(false)
                        } );

            return output;
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query with no inputs.
        /// </summary>
        /// <param name="unitOfWork"></param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <param name="session">Nhibernate session</param>
        /// <returns>query result</returns>
        /// 
        private async Task<IList<E>> 
        EvaluateGetWithNoInputs(
            NhibernateUnitOfWork unitOfWork,
            ResultCardinality    cardinality)
	    {
            IQuery query = unitOfWork.DoGetNamedQuery(Name);
            IList<E> output =
                cardinality == ResultCardinality.ZERO_TO_MANY
                    ? await 
                        query
                            .ListAsync<E>()
                            .ConfigureAwait(false)
                    : new List<E>( 
                        new E[]
                        {
                            await 
                                query
                                    .UniqueResultAsync<E>()
                                    .ConfigureAwait(false)
                        } );

            return output;
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query and returns the number of results.
        /// </summary>
        /// 
        /// <returns>the number of results</returns>
        /// 
        protected async Task<long> 
        EvaluateHas()
        {
			InputKeeper keeper  = GetInputs();
            long        output;
        
            switch ( keeper.Mode )
            {
            case InputMode.NAMED:
                output = 
                    await EvaluateHasWithNamedInputs(unitOfWork, keeper);
                break;
            
            case InputMode.POSITIONAL:
                output = 
                    await EvaluateHasWithPositionalInputs(unitOfWork, keeper);
                break;
            
            default:
                output =
                    await EvaluateHasWithNoInputs(unitOfWork);
                break;
            }

            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private async Task<long> 
        EvaluateHasWithNamedInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper)
        {
            IQuery                     query  = unitOfWork.DoGetNamedQuery(Name);
            IDictionary<String,Object> inputs = keeper.GetNamedInputs();
            long                       output;

            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );

            foreach (KeyValuePair<String,Object> input in inputs)
                if (IsCollectionType(input.Value))
                    query.SetParameterList(input.Key,(IEnumerable)input.Value);
                else
                    query.SetParameter( input.Key,input.Value );
        
            output = 
                HasIntegerReturnType(query)
                    ? await 
                        query
                            .UniqueResultAsync<long>()
                            .ConfigureAwait(false)
                    : (await 
                        query
                            .ListAsync()
                            .ConfigureAwait(false))
                            .Count;

            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private async Task<long> 
        EvaluateHasWithPositionalInputs(
            NhibernateUnitOfWork unitOfWork,
            InputKeeper          keeper)
        {
            IQuery              query    = unitOfWork.DoGetNamedQuery(Name);
            ICollection<Object> inputs   = keeper.GetPositionalInputs();
            int                 position = 0;
            long                output;
        
            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );
        
            foreach (Object input in inputs)
                query.SetParameter( position++,input );
        
            output =
                HasIntegerReturnType(query)
                    ? await 
                        query
                            .UniqueResultAsync<long>()
                            .ConfigureAwait(false)
                    : (await 
                        query
                            .ListAsync()
                            .ConfigureAwait(false))
                            .Count;

            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private async Task<long> 
        EvaluateHasWithNoInputs(NhibernateUnitOfWork unitOfWork)
        {
            IQuery query = unitOfWork.DoGetNamedQuery(Name);
            long   output;

            query.SetMaxResults( 2 );
            query.SetFetchSize( 2 );
        
            output =
                HasIntegerReturnType(query)
                    ? await
                        query
                            .UniqueResultAsync<long>()
                            .ConfigureAwait(false)
                    : (await 
                        query
                            .ListAsync()
                            .ConfigureAwait(false))
                            .Count;

            return output;
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
