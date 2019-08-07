//  ##########################################################################
//  # File Name: AbstractController.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.ComponentModel;
using Strata.Client.Model;
using Strata.Client.PropertyChange;
using Strata.Client.ViewModel;

namespace Strata.Client.Controller
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract 
    class AbstractController<P,V>:
        AbstractPropertyChangeNotifier,
        IController<P,V>
        where P:class
        where V:IViewModel<P>
    {
        public V ViewModel { get; protected set; }

        protected
        AbstractController()
        {
            ViewModel = default(V);
        }

        public abstract void
        Start();

        public abstract void
        Stop();

        protected abstract void
        ProcessPropertyChanged(Object sender,PropertyChangedEventArgs args);

    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract 
    class AbstractController<P,V,M>:
        AbstractController<P,V>,
        IController<P,V,M>
        where P:class
        where V:IViewModel<P>
        where M:IModel
    {
        public M Model { get; protected set; }

        protected
        AbstractController()
        {
            Model = default(M);
        }

        protected
        AbstractController(M model)
        {
            Model = model;
            Model.PropertyChanged += ProcessPropertyChanged;
        }
    }

}

//  ##########################################################################
