//  ##########################################################################
//  # File Name: IExceptionHandler.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IExceptionHandler
    {
        void
        OnException(Exception exception);
    }
}

//  ##########################################################################
