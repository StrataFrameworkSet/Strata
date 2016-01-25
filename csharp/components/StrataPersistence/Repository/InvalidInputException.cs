//  ##########################################################################
//  # File Name: AbstractRepositoryException.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates an error setting an 
    /// input on a <c>IFinder{T}</c> object.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class InvalidInputException:
        RepositoryException
    {
        public 
        InvalidInputException():
            base("Invalid input") {}

        public 
        InvalidInputException(String message): 
            base( message ) {}

        public 
        InvalidInputException(String message,Exception innerException): 
            base( message,innerException ) {}
    }
}

//  ##########################################################################
