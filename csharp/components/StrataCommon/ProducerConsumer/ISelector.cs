//  ##########################################################################
//  # File Name: ISelector.cs
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
    interface ISelector<T>
    {
        bool
        Match(T element);
    }
}

//  ##########################################################################
