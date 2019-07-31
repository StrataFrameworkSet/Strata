//  ##########################################################################
//  # File Name: ProviderBasedBinder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using SystemEx.Injection;

namespace Strata.Foundation.Injection
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
