﻿//  ##########################################################################
//  # File Name: IDomainEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Core.DomainEvent
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IDomainEvent<S>
    {
        string   Name { get; }
        DateTime Timestamp { get; }
        S        Source { get; }
    }
}

//  ##########################################################################
