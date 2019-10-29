//  ##########################################################################
//  # File Name: InMemoryNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.InMemory.UnitOfWOrk;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.InMemory.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class InMemoryNamedQuery<E>:
        AbstractNamedQuery<E>
        where E:class
    {	
        private InMemoryUnitOfWorkProvider itsContext;
	    private Type                       itsType;
	    private IPredicate<E>			   itsPredicate;
	    private IList<E>				   result;
	    private IEnumerator<E>			   current;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public 
        InMemoryNamedQuery(
            InMemoryUnitOfWorkProvider cntx,
            String                     name,
            IPredicate<E>              pred):
            base(name)
        {
            itsContext   = cntx;
		    itsType      = typeof(E);
		    itsPredicate = pred;
		    result   = null;
		    current  = null;
		
		    itsContext.InsertNamedQuery<E>(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public   
        InMemoryNamedQuery(InMemoryNamedQuery<E> other):
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
            return new InMemoryNamedQuery<E>( this );            
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
	    public override Task
	    Execute()
	    {
		    if ( result != null )
			    return Task.CompletedTask;
		
		    result  = GetResults( 
                GetInputs().GetNamedInputs(),
                itsContext.GetEntitiesByType<E>() );
		    current = result.GetEnumerator();
            return Task.CompletedTask;
	    }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<ICollection<E>> 
        GetAll()
        {
		    Execute();
		    return Task.FromResult<ICollection<E>>(result);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<E>> 
        GetUnique()
        {
		    Execute();

            return 
                Task.FromResult(
                    Optional<E>.OfNullable(result.SingleOrDefault()));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<E>> 
        GetNext()
        {
		    Execute();

            try
            {
                current.MoveNext();
                E next = current.Current;
                
                return 
                    Task.FromResult(
                        Optional<E>.OfNullable(next));
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        HasUnique()
        {
            Execute();
            return Task.FromResult(result.Count == 1);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        HasAny()
        {
            Execute();
            return Task.FromResult(result.Count > 0);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        HasNext()
        {
		    Execute();

            try
            {
                E next = current.Current;
                
                return Task.FromResult(true);
            }
            catch (InvalidOperationException)
            {
                return Task.FromResult(false);
            }
        }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
	    protected IList<E> 
	    GetResults(IDictionary<String,Object> inputs,IList<E> objects)
	    {
		    IList<E> output = new List<E>();
		
		    foreach (E entry in objects)
			    if ( itsPredicate.Evaluate( entry,inputs ) )
				    output.Add( entry );
		
		    return output;
	    }

    }
}

//  ##########################################################################
