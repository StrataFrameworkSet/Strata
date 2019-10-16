//  ##########################################################################
//  # File Name: IProvider{T}.cs
//  ##########################################################################


namespace Strata.Foundation.Core.Provider
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// An interface for factory-like objects used in
    /// some dependency injection scenarios. 
    /// </summary>
    ///  
    public
    interface IProvider<T>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new instance of type <c>T</c>.
        /// </summary>
        ///
        /// <returns>new instance of T</returns>
        /// 
        T
        Get();
    }
}

//  ##########################################################################
