//  ##########################################################################
//  # File Name: CopyExtension.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Foundation.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a copy mechanism that is analogous to the
    /// <see cref="!http://en.wikipedia.org/wiki/Prototype_pattern">
    /// Prototype Pattern</see> with 
    /// <see cref="!http://en.wikipedia.org/wiki/Covariant_return_type">
    /// Covariant Returns</see>.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public static
    class CopyExtension
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a copy of this object. 
        /// </summary>
        /// 
        /// <typeparam name="T">Copyable Type</typeparam>
        /// <returns>copy of this object</returns>
        /// 
        /// <postcondition>
        /// result.Equals(copyable) == true
        /// </postcondition>
        /// 
        public static T 
        Copy<T>(this T copyable)
            where T: ICopyable
        {
            return (T)copyable.MakeCopy();
        }
    }
}

//  ##########################################################################
