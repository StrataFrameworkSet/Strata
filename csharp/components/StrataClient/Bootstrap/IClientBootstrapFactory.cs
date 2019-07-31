//  ##########################################################################
//  # File Name: IClientBootstrapFactory.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Windows;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Unity;

namespace Strata.Client.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IClientBootstrapFactory:
        IBootstrapFactory
    {
        DependencyObject
        CreateShell(IUnityContainer container);
    }
}

//  ##########################################################################
