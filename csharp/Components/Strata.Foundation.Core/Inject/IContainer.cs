//  ##########################################################################
//  # File Name: IContainer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents a container of dependency injection bindings and provides
    /// methods for binding types and decorators and getting instances 
    /// generated from these bindings.
    /// </summary>
    ///  
    public
    interface IContainer
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns an instance created from the 
        /// binding mapped to the specified type.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// 
        /// <returns>an instance created from the binding</returns>
        /// 
        T
        GetInstance<T>()
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns an instance created from the 
        /// binding mapped to the specified type and key.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// <param name="key">the binding's key</param>
        /// 
        /// <returns>an instance created from the binding</returns>
        /// 
        T
        GetInstance<T>(string key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns an instance created from the 
        /// binding mapped to the specified type and key.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// <typeparam name="K">the binding's key type</typeparam>
        /// 
        /// <param name="key">the binding's key</param>
        /// 
        /// <returns>an instance created from the binding</returns>
        /// 
        T
        GetInstance<T,K>(K key)
            where T: class
            where K: Attribute;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the container has the specified binding.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// 
        /// <returns>
        /// true if container has specified binding, false otherwise
        /// </returns>
        /// 
        bool
        HasBinding<T>();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the container has the specified binding.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// <param name="key">the binding's key</param>
        /// 
        /// <returns>
        /// true if container has specified binding, false otherwise
        /// </returns>
        /// 
        bool
        HasBinding<T>(string key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the container has the specified binding.
        /// </summary>
        /// 
        /// <typeparam name="T">the binding's type</typeparam>
        /// <typeparam name="K">the binding's key type</typeparam>
        /// 
        /// <param name="key">the binding's key</param>
        /// 
        /// <returns>
        /// true if container has specified binding, false otherwise
        /// </returns>
        /// 
        bool
        HasBinding<T,K>(K key)
            where K: Attribute;
    }
}

//  ##########################################################################
