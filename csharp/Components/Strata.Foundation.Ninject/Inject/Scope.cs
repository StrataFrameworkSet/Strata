//  ##########################################################################
//  # File Name: Scope.cs
//  ##########################################################################

using System;
using System.Security.Cryptography.X509Certificates;

namespace Strata.Foundation.Ninject.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class Scope:
        IEquatable<Scope>
    {
        public static readonly Scope SINGLETON_SCOPE = new SingletonScope();
        public static readonly Scope THREAD_SCOPE = new ThreadScope();
        public static readonly Scope REQUEST_SCOPE = new RequestScope();
        public static readonly Scope NULL_SCOPE = new NullScope();

        public abstract bool 
        Equals(Scope other);

        public static bool
        operator ==(Scope x,Scope y)
        {
            if (x == null)
                if (y == null)
                    return true;
                else
                    return false;
                
            return x.Equals(y);
        }

        public static bool
        operator!=(Scope x,Scope y)
        {
            return !(x == y);
        }
    }
}

//  ##########################################################################
