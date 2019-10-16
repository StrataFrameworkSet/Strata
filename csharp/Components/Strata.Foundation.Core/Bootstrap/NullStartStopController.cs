//  ##########################################################################
//  # File Name: NullStartStopController.cs
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
    class NullStartStopController:
        IStartStopController
    {
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        StartApplication() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        StopApplication() {}
    }
}

//  ##########################################################################
