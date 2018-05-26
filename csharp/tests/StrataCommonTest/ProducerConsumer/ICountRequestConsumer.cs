//  ##########################################################################
//  # File Name: ICountRequestConsumer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
