﻿//  ##########################################################################
//  # File Name: ISelector.cs
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
    interface ISelector<T>
    {
        bool
        Match(T element);
    }
}

//  ##########################################################################
