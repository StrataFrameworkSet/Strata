//  ##########################################################################
//  # File Name: EfNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.EntityFramework.UnitOfWork;
using Strata.Foundation.Utility;
using Strata.Domain.NamedQuery;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Strata.EntityFramework.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an Entity Framework specific implementation of the 
    /// <c>INamedQuery{T}</c> interface based on SqlQuery.
    /// </summary>
    /// 
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EfNamedQuery<T>:
        AbstractNamedQuery<T>
        where T:class
    {
	    private EfUnitOfWork      unitOfWork;
        private String            query;
	    private IList<T>	      result;
	    private IEnumerator<T>	  current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public 
        EfNamedQuery(EfUnitOfWork uow,String finderName,String sql):
            base( finderName )
        {
		    unitOfWork  = uow;
            query       = sql;
		    result      = null;
		    current     = null;     
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public 
        EfNamedQuery(EfNamedQuery<T> other):
            base( other )
        {
            unitOfWork = other.unitOfWork;
            query      = other.query;
            result     = null;
            current    = null;
        } 

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateNamedQuery{T}</c> instance.
        /// </summary>
        /// 
        public override ICopyable 
        MakeCopy()
        {
            return new EfNamedQuery<T>( this );
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
			    InputKeeper keeper  = GetInputs();
			
			    switch ( keeper.Mode )
			    {
			    case InputMode.NAMED:
				    result = 
				        EvaluateGetWithNamedInputs( keeper,cardinality );
				    break;
				
			    case InputMode.POSITIONAL:
				    throw new NotImplementedException("Positional inputs not supported.");
				
			    default:
				    result = 
				        EvaluateGetWithNoInputs( cardinality );
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
        /// <param name="session">Nhibernate session</param>
        /// <param name="keeper">input keeper</param>
        /// <param name="cardinality">expected result cardinality</param>
        /// <returns>query result</returns>
        /// 
        private IList<T> 
        EvaluateGetWithNamedInputs(
    	    InputKeeper       keeper,
    	    ResultCardinality cardinality)
        {
    	    IList<T> output = unitOfWork.DoQuery<T>(
                                  query,
                                  keeper.GetNamedInputs());
    	     
            if ( cardinality == ResultCardinality.ZERO_TO_MANY )
                return output;

            if ( output.Count > 1 )
                throw new NotUniqueException("result is not unique");

            return output;
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
	    EvaluateGetWithNoInputs(ResultCardinality cardinality)
	    {
    	    IList<T> output = unitOfWork.DoQuery<T>(query);
    	     
            if ( cardinality == ResultCardinality.ZERO_TO_MANY )
                return output;

            if ( output.Count > 1 )
                throw new NotUniqueException("result is not unique");

            return output;
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
            InputKeeper keeper = GetInputs();
        
            switch ( keeper.Mode )
            {
            case InputMode.NAMED:
                return 
                    EvaluateHasWithNamedInputs( keeper );
            
            case InputMode.POSITIONAL:
                throw new NotImplementedException( "Positional inputs not supported.");
            
            default:
                return 
                    EvaluateHasWithNoInputs();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private long 
        EvaluateHasWithNamedInputs(InputKeeper keeper)
        {
    	    IList<T> output = unitOfWork.DoQuery<T>(
                                  query,
                                  keeper.GetNamedInputs());
    	     
            return output.Count;  
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private long 
        EvaluateHasWithNoInputs()
        {
    	    IList<T> output = unitOfWork.DoQuery<T>(query);
    	     
            return output.Count;  
        }

    }
}

//  ##########################################################################
