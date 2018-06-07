//  ##########################################################################
//  # File Name: DecoratorMap.cs
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
    class DecoratorMap:
        IDecoratorMap
    {
        private IDictionary<Type,Stack<IDecoratorFactory>> itsDecorators;
        private IContainer                                 itsContainer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        DecoratorMap()
        {
            itsDecorators = new Dictionary<Type,Stack<IDecoratorFactory>>();
            itsContainer = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public IDecoratorMap 
        InsertDecorator<T>(IDecoratorFactory factory) 
        {
            return InsertDecorator(typeof(T),factory);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public IDecoratorMap 
        InsertDecorator(Type target,IDecoratorFactory factory)
        {
            if (!HasDecorators(target))
                itsDecorators.Add(target,new Stack<IDecoratorFactory>());

            itsDecorators[target].Push(factory);

            if (MustInitialize(factory))
                Initialize(factory);

            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public IReadOnlyCollection<IDecoratorFactory>
        GetDecorators<T>()
        {
            return GetDecorators(typeof(T));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public IReadOnlyCollection<IDecoratorFactory>
        GetDecorators(Type target)
        {
            return itsDecorators[target];
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasDecorators<T>()
        {
            return HasDecorators(typeof(T));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasDecorators(Type target)
        {
            return itsDecorators.ContainsKey(target);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public void
        Initialize(IContainer container)
        {
            itsContainer = container;

            foreach (
                Stack<IDecoratorFactory> decorators in itsDecorators.Values)
                foreach (IDecoratorFactory decorator in decorators)
                    if (MustInitialize(decorator))
                        Initialize(decorator); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual bool
        MustInitialize(IDecoratorFactory factory)
        {
            return itsContainer != null && factory is IContainerHolder;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void
        Initialize(IDecoratorFactory factory)
        {
            if (factory is IContainerHolder)
                ((IContainerHolder)factory).Container = itsContainer;
        }
    }
}

//  ##########################################################################
