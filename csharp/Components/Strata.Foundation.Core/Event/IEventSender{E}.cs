//  ##########################################################################
//  # File Name: IEventSender{E}.cs
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
    interface IEventSender<E>:
        IDisposable
    {
        IEventSender<E>
        Open();

        IEventSender<E>
        Close();

        IEventSender<E>
        Send(E evnt);

        bool
        IsOpen();

    }
}

//  ##########################################################################
