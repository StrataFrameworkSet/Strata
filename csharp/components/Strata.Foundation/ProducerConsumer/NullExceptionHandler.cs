//  ##########################################################################
//  # File Name: NullExceptionHandler.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.ProducerConsumer
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
