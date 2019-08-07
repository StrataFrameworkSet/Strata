//  ##########################################################################
//  # File Name: DapperNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using Dapper;
using Strata.Domain.NamedQuery;
using Strata.Foundation.Utility;
using Strata.Dapper.UnitOfWork;

namespace Strata.Dapper.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class DapperNamedQuery<T>:
        AbstractNamedQuery<T>
        where T:class
    {	
        private DapperUnitOfWork unitOfWork;
        private readonly string  querySql;
	    private IList<T>	     result;
	    private IEnumerator<T>   current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public 
        DapperNamedQuery(
            DapperUnitOfWork uow,
            string           name,
            string           sql):
            base(name)
        {
            unitOfWork = uow;
            querySql   = sql;
		    result     = null;
		    current    = null;
		
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public   
        DapperNamedQuery(DapperNamedQuery<T> other):
            base( other )
        {
            unitOfWork = other.unitOfWork;
            querySql   = other.querySql;
		    result     = null;
		    current    = null;
        } 

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override ICopyable
        MakeCopy()
        {
            return new DapperNamedQuery<T>( this );            
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Clear()
        {
            ClearInputs();
            result  = null;
            current = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
	    public override void
	    Execute()
	    {
		    if ( result != null )
			    return;
		
		    result  = GetResults( GetInputs().GetNamedInputs() );
		    current = result.GetEnumerator();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override ICollection<T> 
        GetAll()
        {
		    Execute();
		    return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        GetUnique()
        {
		    Execute();

            return result.SingleOrDefault();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        GetNext()
        {
		    Execute();

            try
            {
                current.MoveNext();
                T next = current.Current;
                
                return next;
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        HasUnique()
        {
            Execute();
            return result.Count == 1;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        HasAny()
        {
            Execute();
            return result.Count > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        HasNext()
        {
		    Execute();

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
        /// 
        /// </summary>
        /// 
	    protected IList<T> 
	    GetResults(IDictionary<string,object> inputs)
        {
            return
                unitOfWork
                    .Connection
                    .Query<T>(querySql,inputs,unitOfWork.Transaction)
                    .ToList();
        }

    }
}

//  ##########################################################################
