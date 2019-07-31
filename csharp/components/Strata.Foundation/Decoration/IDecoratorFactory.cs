//  ##########################################################################
//  # File Name: IDecoratorFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IDecoratorFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///        
        T
        Create<T>(T target)
            where T : class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///        
        object
        Create(Type interfaceType,object target);    }
}

//  ##########################################################################
