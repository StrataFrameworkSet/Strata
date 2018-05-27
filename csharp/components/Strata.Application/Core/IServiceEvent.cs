//  ##########################################################################
//  # File Name: IServiceEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Application.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IServiceEvent<S>
    {
        string   Name { get; }
        DateTime Timestamp { get; }
        S        Source { get; }
    }
}

//  ##########################################################################
