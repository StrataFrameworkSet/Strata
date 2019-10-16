//  ##########################################################################
//  # File Name: IEventListener{E}.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Event
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IEventListener<E>
    {
        void
        OnEvent(E evnt);

        void
        OnException(Exception exception);
    }
}

//  ##########################################################################
