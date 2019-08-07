//  ##########################################################################
//  # File Name: ICountRequestProducer.cs
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
    interface ICountRequestProducer:
        IProducer<CountRequest>
    {
        long
        GetCount();
    }
}

//  ##########################################################################
