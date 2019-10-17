//  ##########################################################################
//  # File Name: NullScope.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Foundation.Ninject.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NullScope:
        Scope
    {
        public override bool 
        Equals(Scope other)
        {
            return other is NullScope;
        }
    }
}

//  ##########################################################################
