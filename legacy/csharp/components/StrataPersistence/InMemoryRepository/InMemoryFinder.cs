//  ##########################################################################
//  # File Name: InMemoryFinder.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Common.Utility;
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
    class InMemoryFinder<T>:
        AbstractFinder<T>
        where T:class
    {	
        private InMemoryRepositoryContext itsContext;
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
        InMemoryFinder(
            InMemoryRepositoryContext cntx,
            String                    name,
            IPredicate<T>             pred):
            base(name)
        {
            itsContext   = cntx;
		    itsType      = typeof(T);
		    itsPredicate = pred;
		    result   = null;
		    current  = null;
		
		    itsContext.InsertFinder<T>(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public   
        InMemoryFinder(InMemoryFinder<T> other):
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
            return new InMemoryFinder<T>( this );            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetAll()"/>
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
        /// <see cref="IFinder{T}.HasNext()"/>
        /// </summary>
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
        /// <summary>
        /// <see cref="IFinder{T}.GetAll()"/>
        /// </summary>
        /// 
        public override ICollection<T> 
        GetAll()
        {
		    Execute();
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
		    Execute();

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
		    Execute();

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
            Execute();
            return result.Count == 1;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasAny()"/>
        /// </summary>
        /// 
        public override bool 
        HasAny()
        {
            Execute();
            return result.Count > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.HasNext()"/>
        /// </summary>
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
        /// <see cref="IFinder{T}.HasNext()"/>
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
