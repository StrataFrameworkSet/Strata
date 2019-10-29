//  ##########################################################################
//  # File Name: AbstractNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>INamedQuery{T}</c> types.
    /// </summary>
    /// 
    /// <typeparam name="E">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractNamedQuery<E>:
        AbstractMethod,
        INamedQuery<E>
        where E: class
    {

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractNamedQuery.
        /// </summary>
        /// 
        /// <param name="name">finder name</param>
        /// 
	    protected 
	    AbstractNamedQuery(string name):
            base( name ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractNamedQuery.
        /// </summary>
        /// 
        /// <param name="other">other finder</param>
        /// 
	    protected 
	    AbstractNamedQuery(AbstractNamedQuery<E> other):
            base( other ) {}


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.GetAll()"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<ICollection<E>> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<Optional<E>> 
        GetUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<Optional<E>> 
        GetNext();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<bool> 
        HasUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<bool> 
        HasAny();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract Task<bool> 
        HasNext();
    }
}

//  ##########################################################################
