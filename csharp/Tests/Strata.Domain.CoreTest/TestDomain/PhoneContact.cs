//  ##########################################################################
//  # File Name: PhoneContact.cs
//  ##########################################################################

using System;
using Strata.Foundation.Core.Value;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class PhoneContact:
        AbstractContactInformation
    {
        public virtual PhoneNumber Phone { get; set; }
    }
}

//  ##########################################################################
