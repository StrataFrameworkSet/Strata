//  ##########################################################################
//  # File Name: IStartStopController.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IStartStopController
    {
        void
        StartApplication();

        void
        StopApplication();
    }
}

//  ##########################################################################
