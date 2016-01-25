//  ##########################################################################
//  # File Name: AbstractFinder.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>IFinder{T}</c> types.
    /// </summary>
    /// 
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractFinder<T>:
        AbstractRepositoryMethod,
        IFinder<T>
    {

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractFinder.
        /// </summary>
        /// 
        /// <param name="name">finder name</param>
        /// 
	    protected 
	    AbstractFinder(String name):
            base( name ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractFinder.
        /// </summary>
        /// 
        /// <param name="other">other finder</param>
        /// 
	    public 
	    AbstractFinder(AbstractFinder<T> other):
            base( other ) {}


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.GetAll()"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract ICollection<T> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract T 
        GetUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract T 
        GetNext();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasUnique();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasAny();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IFinder{T}.)"/>
        /// Subclasses must implement this method.
        /// </summary>
        /// 
        public abstract bool 
        HasNext();
    }
}

//  ##########################################################################
