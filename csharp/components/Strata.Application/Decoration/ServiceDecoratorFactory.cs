//  ##########################################################################
//  # File Name: ServiceDecoratorFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.UnitOfWork;
using Strata.Foundation.Decoration;
using Strata.Foundation.Injection;
using Strata.Foundation.Logging;
using System;
using SystemEx.Injection;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class ServiceDecoratorFactory:
        DecoratorFactory<ServiceDecorator>,
        IContainerHolder
    {
        public IContainer Container { get; set; }
        public int        MaxRetries { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        ServiceDecoratorFactory() :
            this(null,1) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        ServiceDecoratorFactory(int maxRetries):
            this(null,maxRetries) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        ServiceDecoratorFactory(IContainer container,int maxRetries)
        {
            Container  = container;
            MaxRetries = maxRetries;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        protected override ServiceDecorator 
        GetDecorator()
        {
            if (Container == null)
                throw
                    new NullReferenceException(
                        "Container must be set before use.");

            return 
                new ServiceDecorator(
                    Container.GetInstance<IUnitOfWorkProvider>(),
                    Container.GetInstance<ILogger>(),
                    MaxRetries);
        }
    }
}

//  ##########################################################################
