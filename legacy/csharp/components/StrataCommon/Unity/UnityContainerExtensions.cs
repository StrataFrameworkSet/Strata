using System;
using Microsoft.Practices.Unity;

namespace Strata.Common.Unity
{
    /// <summary>
    /// Provides extension methods for <see cref="IUnityContainer"/> implementations.
    /// </summary>
    public static class UnityContainerExtensions
    {
        /// <summary>
        /// An extension method for <see cref="IUnityContainer"/> which will attempt to register the given type mapping, but will not 
        /// override an existing mapping.   The default method provided by the <see cref="IUnityContainer"/> will overwrite.
        /// </summary>
        /// <param name="container">The container to work on.</param>
        /// <param name="registerAsSingleton">Indicates whether the instance should be registered as a singleton within the container.</param>
        public static bool TryRegisterType<TFrom, TTo>(this IUnityContainer container, bool registerAsSingleton = false)
            where TTo : class, TFrom
        {
            if (container.IsRegistered<TFrom>())
            {
                return false;
            }
            if (registerAsSingleton)
            {
                container.RegisterType<TFrom, TTo>(new ContainerControlledLifetimeManager());
            }
            else
            {
                container.RegisterType<TFrom, TTo>();
            }

            return true;
        }

        /// <summary>
        /// An extension method for <see cref="IUnityContainer"/> which will attempt to register the given type mapping, but will not 
        /// override an existing mapping.   The default method provided by the <see cref="IUnityContainer"/> will overwrite.
        /// </summary>
        /// <param name="container">The container to work on.</param>
        /// <param name="fromType">The type to map from.</param>
        /// <param name="toType">The type to map to.</param>
        /// <param name="registerAsSingleton">Indicates whether the instance should be registered as a singleton within the container.</param>
        public static bool TryRegisterType(this IUnityContainer container, Type fromType, Type toType, bool registerAsSingleton = false)
        {
            if (container.IsRegistered(fromType))
            {
                return false;
            }
            if (registerAsSingleton)
            {
                container.RegisterType(fromType, toType, new ContainerControlledLifetimeManager());
            }
            else
            {
                container.RegisterType(fromType, toType);
            }

            return true;
        }
    }
}
