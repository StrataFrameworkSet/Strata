//  ##########################################################################
//  # File Name: AbstractModel.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Client.PropertyChange;

namespace Strata.Client.Model
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract 
    class AbstractModel:
        AbstractPropertyChangeNotifier,
        IModel {}
}

//  ##########################################################################
