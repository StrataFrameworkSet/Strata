//  ##########################################################################
//  # File Name: INamedQuery.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System.Threading.Tasks;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides abstractions for retrieving entities from a repository
    /// and determining the existence of entities in a repository. 
    /// </summary>
    /// 
    /// <typeparam name="E">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface INamedQuery<E>:
        IMethod
        where E: class
    {

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns all results of the find process. 
        /// </summary>
        /// 
        /// <returns>all results</returns>
        /// 
	    Task<ICollection<E>> 
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
	    Task<Optional<E>> 
        GetUnique();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the next result during the process of finding entities.
        /// </summary>
        /// 
        /// <returns>the next entity in the result or null</returns>
        /// 
	    Task<Optional<E>> 
        GetNext();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is a unique result.
        /// </summary>
        /// 
        /// <returns>true if unique result, false otherwise</returns>
        /// 
	    Task<bool> 
        HasUnique();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is any results.
        /// </summary>
        /// 
        /// <returns>true if any results, false otherwise</returns>
        /// 
	    Task<bool> 
        HasAny();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if there is a next result.
        /// </summary>
        /// 
        /// <returns>true if next result, false otherwise</returns>
        /// 
	    Task<bool> 
        HasNext();

    }
}

//  ##########################################################################
