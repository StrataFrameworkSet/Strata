//  ##########################################################################
//  # File Name: ICountRequestConsumer.cs
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
    interface ICountRequestConsumer:
        IConsumer<CountRequest>
    {
        long
        GetCount();
    }
}

//  ##########################################################################
