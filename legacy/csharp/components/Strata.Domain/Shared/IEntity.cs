//  ##########################################################################
//  # File Name: IEntity.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Shared
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IEntity<K>
    {
        K        PrimaryId { get; set; }
        int      Version { get; set; }

        DateTime Created { get; set; }
        DateTime LastModified { get; set; }
    }
}

//  ##########################################################################
