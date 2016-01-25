//  ##########################################################################
//  # File Name: IController.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.ComponentModel;
using Strata.Client.Model;
using Strata.Client.ViewModel;

namespace Strata.Client.Controller
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IController<P,out V>:
        INotifyPropertyChanged
        where P:class
        where V:IViewModel<P>
    {
        V ViewModel { get; }

        void 
        NotifyPropertyChanged(String property);

        void
        Start();

        void
        Stop();
    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IController<P,out V,out M>:
        IController<P,V>
        where P:class
        where V:IViewModel<P>
        where M:IModel
    {
        M Model { get; }
    }
}

//  ##########################################################################
