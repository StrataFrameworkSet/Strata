//  ##########################################################################
//  # File Name: TargetBasedBinder.cs
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
    public
    class TargetBasedBinder<T,U>:
        AbstractBinder<T>
        where U: T
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        TargetBasedBinder(ISourceBindingBuilder<T> builder,Type scope): 
            base(builder,scope) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Bind()
        {
            Builder
                .ToType<U>()
                .WithScope(Scope);
        }
    }
}

//  ##########################################################################
