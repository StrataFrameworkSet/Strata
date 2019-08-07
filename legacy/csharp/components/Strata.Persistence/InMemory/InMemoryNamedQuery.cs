//  ##########################################################################
//  # File Name: InMemoryNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Foundation.Utility;
using Strata.Persistence.NamedQuery;

namespace Strata.Persistence.InMemory
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class InMemoryNamedQuery<T>:
        AbstractNamedQuery<T>
        where T:class
    {	
        private InMemoryUnitOfWorkProvider itsContext;
	    private Type                      itsType;
	    private IPredicate<T>			  itsPredicate;
	    private IList<T>				  result;
	    private IEnumerator<T>			  current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public 
        InMemoryNamedQuery(
            InMemoryUnitOfWorkProvider cntx,
            String                     name,
            IPredicate<T>              pred):
            base(name)
        {
            itsContext   = cntx;
		    itsType      = typeof(T);
		    itsPredicate = pred;
		    result   = null;
		    current  = null;
		
		    itsContext.InsertNamedQuery<T>(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public   
        InMemoryNamedQuery(InMemoryNamedQuery<T> other):
            base( other )
        {
		    itsContext   = other.itsContext;
		    itsType      = other.itsType;
		    itsPredicate = other.itsPredicate;
		    result       = null;
		    current      = null;
        } 

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override ICopyable
        MakeCopy()
        {
            return new InMemoryNamedQuery<T>( this );            
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
		
		    result  = GetResults( 
                GetInputs().GetNamedInputs(),
                itsContext.GetEntitiesByType<T>() );
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
	    GetResults(IDictionary<String,Object> inputs,IList<T> objects)
	    {
		    IList<T> output = new List<T>();
		
		    foreach (T entry in objects)
			    if ( itsPredicate.Evaluate( entry,inputs ) )
				    output.Add( entry );
		
		    return output;
	    }

    }
}

//  ##########################################################################
