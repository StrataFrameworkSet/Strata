//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class for all all <c>IRepository</c> types. Provides a means
    /// of implementing the Repository pattern by using the <a href=
    /// "http://tinyurl.com/fifx-arch/Patterns/DesignPatternsBook/pat4bfs.htm">
    /// Bridge</a> design pattern to decouple domain specific 
    /// repository interfaces from repository provider implementations.
    /// </summary>
    /// 
    /// <typeparam name="K">primary key type</typeparam>
    /// <typeparam name="T">entity type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class AbstractRepository<K,T>:
        IRepository
        where T:class
    {
        public IRepositoryContext 
        Context
        {
            get { return Provider.Context; }
        }

        public IUnitOfWork 
        UnitOfWork
        {
            get { return Context.GetUnitOfWork(); }
        }

        protected IRepositoryProvider<K,T> 
        Provider { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepository{K,T}</c> instance.
        /// </summary>
        /// 
        public
        AbstractRepository(IRepositoryProvider<K,T> provider)
        {
            Provider = provider;
        } 
    }
}

//  ##########################################################################
