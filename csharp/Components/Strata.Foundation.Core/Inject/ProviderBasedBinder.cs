//  ##########################################################################
//  # File Name: ProviderBasedBinder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Core.Provider;
using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class ProviderBasedBinder<T>:
        AbstractBinder<T>
    {
        protected IProvider<T> Provider { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        ProviderBasedBinder(
            ISourceBindingBuilder<T> builder,
            IProvider<T>             provider,
            Type                     scope): 
            base(builder,scope)
        {
            Provider = provider;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Bind()
        {
            Builder
                .ToProvider(Provider)
                .WithScope(Scope);
        }
    }
}

//  ##########################################################################
