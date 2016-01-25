//  ##########################################################################
//  # File Name: IFinder.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides abstractions for retrieving entities from a repository
    /// and determining the existence of entities in a repository. 
    /// </summary>
    /// 
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IFinder<T>:
        IRepositoryMethod
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns all results of the find process. 
        /// </summary>
        /// 
        /// <returns>all results</returns>
        /// 
	    ICollection<T> 
	    GetAll();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the unique result of finding an entity. 
        /// </summary>
        /// 
        /// <returns>unique entity</returns>
        /// 
        /// <exception cref="NotUniqueException">
        /// Result is not unique.
        /// </exception>
        /// 
	    T 
	    GetUnique();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the next result during the process of finding entities.
        /// </summary>
        /// 
        /// <returns>the next entity in the result or null</returns>
	    T 
	    GetNext();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is a unique result.
        /// </summary>
        /// 
        /// <returns>true if unique result, false otherwise</returns>
        /// 
	    bool
	    HasUnique();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is any results.
        /// </summary>
        /// 
        /// <returns>true if any results, false otherwise</returns>
        /// 
	    bool
	    HasAny();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is a next result.
        /// </summary>
        /// 
        /// <returns>true if next result, false otherwise</returns>
        /// 
	    bool 
	    HasNext();

    }
}

//  ##########################################################################
