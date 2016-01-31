//  ##########################################################################
//  # File Name: NullExceptionHandler.cs
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
    class NullExceptionHandler :
        IExceptionHandler
    {
        public void
        OnException(Exception exception) {}
    }
}

//  ##########################################################################
