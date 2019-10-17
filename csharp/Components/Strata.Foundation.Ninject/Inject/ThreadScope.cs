//  ##########################################################################
//  # File Name: ThreadScope.cs
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
    class ThreadScope:
        Scope
    {
        public override bool
        Equals(Scope other)
        {
            return other is ThreadScope;
        }
    }
}

//  ##########################################################################
