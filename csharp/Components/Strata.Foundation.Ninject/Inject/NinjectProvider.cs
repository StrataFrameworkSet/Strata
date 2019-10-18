//  ##########################################################################
//  # File Name: NinjectProvider.cs
//  ##########################################################################

using Ninject.Activation;

namespace Strata.Foundation.Ninject.Inject
{
    using Inject = Strata.Foundation.Core.Inject;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class NinjectProvider<T>:
        Provider<T>
    {
        private readonly Inject.IProvider<T> itsAdaptee;

        public 
        NinjectProvider(Inject.IProvider<T> adaptee)
        {
            itsAdaptee = adaptee;
        }

        protected override T 
        CreateInstance(IContext context)
        {
            return itsAdaptee.Get();
        }
    }
}

//  ##########################################################################
