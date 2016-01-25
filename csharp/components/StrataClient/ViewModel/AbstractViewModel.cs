//  ##########################################################################
//  # File Name: AbstractViewModel.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.ComponentModel;
using Strata.Client.PropertyChange;

namespace Strata.Client.ViewModel
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class AbstractViewModel<P>:
        AbstractPropertyChangeNotifier,
        IViewModel<P>
        where P:class
    {

        public P CommandProvider { get; set; }

        protected
        AbstractViewModel()
        {
            CommandProvider = null;
        }
    }
}

//  ##########################################################################
