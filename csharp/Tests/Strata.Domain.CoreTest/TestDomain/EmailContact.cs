//  ##########################################################################
//  # File Name: EmailContact.cs
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
    class EmailContact:
        AbstractContactInformation
    {
        public virtual EmailAddress Email { get; set; }
    }
}

//  ##########################################################################
