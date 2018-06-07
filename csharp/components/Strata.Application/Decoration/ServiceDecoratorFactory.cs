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
        ServiceDecoratorFactory():
            this(null,1) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        ServiceDecoratorFactory(IContainer container,int maxTries)
        {
            Container  = container;
            MaxRetries = maxTries;
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
