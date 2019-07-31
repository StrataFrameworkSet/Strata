//  ##########################################################################
//  # File Name: AtomicBoolean.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Threading;

namespace Strata.Foundation.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class AtomicBoolean
    {
        private int itsValue;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        AtomicBoolean(bool value)
        {
            itsValue = value ? 1 : 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void
        Set(bool value)
        {
            Interlocked.Exchange( ref itsValue,value ? 1 : 0 ); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        CompareAndSet(bool expected,bool update)
        {
            int temp = expected ? 1 : 0;

            return 
                Interlocked.CompareExchange( 
                    ref itsValue,
                    update ? 1 : 0,
                    temp ) == temp;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        Get()
        {
            return Interlocked.Add( ref itsValue,0 ) == 1;
        }
    }
}

//  ##########################################################################
