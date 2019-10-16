//  ##########################################################################
//  # File Name: IApplicationFactory{M,C}.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################


namespace Strata.Foundation.Core.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IApplicationFactory<M,C>
        where M: class
        where C: class
    {
        void
        BindLogger(M module);

        void
        BindStartStopController(M module);

        C
        CreateContainer();
    }
}

//  ##########################################################################
