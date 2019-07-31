//  ##########################################################################
//  # File Name: AbstractPropertyChangeNotifier.cs
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
    public abstract 
    class AbstractPropertyChangeNotifier:
        IPropertyChangeNotifier
    {
        public event PropertyChangedEventHandler PropertyChanged;

        public void
        NotifyPropertyChanged(String property)
        {
            PropertyChangedEventHandler handler = PropertyChanged;

            if (handler != null)
                handler(this, new PropertyChangedEventArgs(property));          
        }
    }
}

//  ##########################################################################
