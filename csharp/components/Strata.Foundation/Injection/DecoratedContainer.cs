//  ##########################################################################
//  # File Name: DecoratedContainer.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Decoration;
using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class DecoratedContainer:
        IContainer
    {
        private IContainer    itsTarget;
        private IDecoratorMap itsDecorators;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        DecoratedContainer(IContainer target,IDecoratorMap decorators)
        {
            itsTarget = target;
            itsDecorators = decorators;
            itsDecorators.Initialize(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T>()
            where T: class
        {
            return Decorate(itsTarget.GetInstance<T>());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T>(string key)
            where T: class
        {
            return Decorate(itsTarget.GetInstance<T>(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T,K>(K key)
            where T: class
            where K: Attribute
        {
            return Decorate(itsTarget.GetInstance<T,K>(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T>()
        {
            return itsTarget.HasBinding<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T>(string key)
        {
            return itsTarget.HasBinding<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T,K>(K key) 
            where K: Attribute
        {
            return itsTarget.HasBinding<T,K>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        protected T
        Decorate<T>(T target)
            where T: class
        {
            T instance = null;

            if (!itsDecorators.HasDecorators<T>())
                return target;

            instance = target;

            foreach (
                IDecoratorFactory factory in
                    itsDecorators.GetDecorators<T>())
                instance = factory.Create(instance);

            return instance;
        }
    }
}

//  ##########################################################################
