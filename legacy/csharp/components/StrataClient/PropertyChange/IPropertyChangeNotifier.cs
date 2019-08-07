//  ##########################################################################
//  # File Name: IPropertyChangeNotifier.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Strata.Client.PropertyChange
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IPropertyChangeNotifier: 
        INotifyPropertyChanged
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Notifies handlers of a PropertyChangeEvent.
        /// </summary>
        /// 
        /// <param name="property">name of property that changed</param>
        /// 
        void NotifyPropertyChanged(String property);
    }
}

//  ##########################################################################
