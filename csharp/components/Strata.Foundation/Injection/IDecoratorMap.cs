//  ##########################################################################
//  # File Name: IDecoratorMap.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Decoration;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IDecoratorMap
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        IDecoratorMap
        InsertDecorator<T>(IDecoratorFactory factory);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        IReadOnlyCollection<IDecoratorFactory>
        GetDecorators<T>();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        IReadOnlyCollection<IDecoratorFactory>
        GetDecorators(Type target);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        bool
        HasDecorators<T>();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        bool
        HasDecorators(Type target);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        void
        Initialize(IContainer container);
    }
}

//  ##########################################################################
