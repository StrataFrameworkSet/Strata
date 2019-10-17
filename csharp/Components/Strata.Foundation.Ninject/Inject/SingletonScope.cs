//  ##########################################################################
//  # File Name: SingletonScope.cs
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
    class SingletonScope:
        Scope
    {
        public override bool
        Equals(Scope other)
        {
            return other is SingletonScope;
        }
    }
}

//  ##########################################################################
