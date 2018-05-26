//  ##########################################################################
//  # File Name: AbstractBinder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractBinder<T>:
        IBinder<T>
    {
        protected ISourceBindingBuilder<T> Builder { get; set; }
        protected Type                     Scope { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        AbstractBinder(ISourceBindingBuilder<T> builder,Type scope)
        {
            Builder = builder;
            Scope   = scope;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void
        Bind();


    }
}

//  ##########################################################################
