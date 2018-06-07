//  ##########################################################################
//  # File Name: DecoratorFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Castle.DynamicProxy;
using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class DecoratorFactory<D>:
        IDecoratorFactory
        where D: Decorator, new()
    {
        private ProxyGenerator itsImp;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        DecoratorFactory()
        {
            itsImp = new ProxyGenerator();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        Create<T>(T target)
            where T: class
        {
            Type type = typeof(T);

            if (!type.IsInterface)
                throw new ArgumentException("Type T must be an interface");

            return 
                itsImp.CreateInterfaceProxyWithTargetInterface(
                    target,
                    GetDecorator());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual D
        GetDecorator()
        {
            return new D();
        }
    }
}

//  ##########################################################################
