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
        public IContainer       Target { get; protected set; }
        protected IDecoratorMap Decorators { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected
        DecoratedContainer()
        {
            Target = null;
            Decorators = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        DecoratedContainer(IContainer target,IDecoratorMap decorators)
        {
            Target = target;
            Decorators = decorators;
            Decorators.Initialize(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T>()
            where T: class
        {
            return Decorate(Target.GetInstance<T>());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T>(string key)
            where T: class
        {
            return Decorate(Target.GetInstance<T>(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public T 
        GetInstance<T,K>(K key)
            where T: class
            where K: Attribute
        {
            return Decorate(Target.GetInstance<T,K>(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T>()
        {
            return Target.HasBinding<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T>(string key)
        {
            return Target.HasBinding<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        HasBinding<T,K>(K key) 
            where K: Attribute
        {
            return Target.HasBinding<T,K>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        protected T
        Decorate<T>(T target)
            where T : class
        {
            T instance = null;

            if (!Decorators.HasDecorators<T>())
                return target;

            instance = target;

            foreach (
                IDecoratorFactory factory in
                    Decorators.GetDecorators<T>())
                instance = factory.Create(instance);

            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        protected object
        Decorate(Type type,object target)
        {
            object instance = null;

            if (!Decorators.HasDecorators(type))
                return target;

            instance = target;

            foreach (
                IDecoratorFactory factory in
                    Decorators.GetDecorators(type))
                instance = factory.Create(type,instance);

            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void
        Initialize(IContainer target,IDecoratorMap decorators)
        {
            Target = target;
            Decorators = decorators;
            Decorators.Initialize(this);
        }
    }
}

//  ##########################################################################
