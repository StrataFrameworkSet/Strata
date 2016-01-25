//  ##########################################################################
//  # File Name: IViewModel.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

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
    public 
    interface IViewModel<P>:
        IPropertyChangeNotifier
        where P:class
    {
        P CommandProvider { get;set; }
    }
}

//  ##########################################################################
