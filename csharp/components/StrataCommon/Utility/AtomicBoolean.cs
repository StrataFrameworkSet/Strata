//  ##########################################################################
//  # File Name: AtomicBoolean.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Threading;

namespace Strata.Common.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class AtomicBoolean
    {
        private int value;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        AtomicBoolean(bool val)
        {
            value = val ? 1 : 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void
        Set(bool val)
        {
            Interlocked.Exchange( ref value,val ? 1 : 0 ); 
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
                    ref value,
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
            return Interlocked.Add( ref value,0 ) == 1;
        }
    }
}

//  ##########################################################################
