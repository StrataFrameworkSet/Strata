//  ##########################################################################
//  # File Name: UnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    enum UnitOfWorkState
    {
        ACTIVE,
        COMMITTED,
        ROLLED_BACK
    }
}

//  ##########################################################################
