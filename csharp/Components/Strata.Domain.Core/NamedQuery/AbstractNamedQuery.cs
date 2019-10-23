//  ##########################################################################
//  # File Name: AbstractNamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>INamedQuery{T}</c> types.
    /// </summary>
    /// 
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractNamedQuery<T>:
        AbstractMethod,
        INamedQuery<T>
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
	    public 
	    AbstractNamedQuery(AbstractNamedQuery<T> other):
            base( other ) {}


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.GetAll()"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract ICollection<T> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract T 
        GetUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract T 
        GetNext();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasAny();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="INamedQuery{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasNext();
    }
}

//  ##########################################################################
