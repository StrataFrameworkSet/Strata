//  ##########################################################################
//  # File Name: MultiCauseException.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class MultiCauseException:
        Exception
    {
        public IList<Exception> Causes { get; protected set; }

        public
        MultiCauseException():
            this(
                "This exception is an aggregate of multiple exceptions",
                new List<Exception>()) {}
       
        public
        MultiCauseException(params Exception[] causes):
            this(new List<Exception>(causes)) {}

        public
        MultiCauseException(string message,params Exception[] causes):
            this(message,new List<Exception>(causes)) {}

        public
        MultiCauseException(IList<Exception> causes):
            this(
                "This exception is an aggregate of multiple exceptions",
                causes) {}

        public
        MultiCauseException(string message,IList<Exception> causes):
            base(message)
        {
            Causes = causes;
        }
    }
}

//  ##########################################################################
