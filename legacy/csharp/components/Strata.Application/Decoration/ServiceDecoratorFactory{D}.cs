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
    public abstract
    class ServiceDecoratorFactory<D>:
        DecoratorFactory<D>,
        IContainerHolder
        where D: ServiceDecorator, new()
    {
        public IContainer Container { get; set; }
        public int        MaxRetries { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public
        ServiceDecoratorFactory(IContainer container,int maxTries)
        {
            Container = container;
            MaxRetries = maxTries;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        protected override D
        GetDecorator()
        {
            throw 
                new InvalidOperationException(
                    "This method must be overridden");
        }
    }
}

//  ##########################################################################
