//  ##########################################################################
//  # File Name: AbstractRepositoryException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates an error setting an 
    /// input on a <c>INamedQuery{T}</c> object.
    /// </summary>
    /// 
    public
    class InvalidInputException:
        Exception
    {
        public 
        InvalidInputException():
            base("Invalid input") {}

        public 
        InvalidInputException(string message): 
            base( message ) {}

        public 
        InvalidInputException(string message,Exception innerException): 
            base( message,innerException ) {}
    }
}

//  ##########################################################################
