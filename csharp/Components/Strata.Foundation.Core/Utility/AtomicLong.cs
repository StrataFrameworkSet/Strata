//  ##########################################################################
//  # File Name: AtomicLong.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Threading;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class AtomicLong
    {
        private long itsValue;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        AtomicLong(long val)
        {
            itsValue = val;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        AddAndGet(long delta)
        {
            return Interlocked.Add( ref itsValue,delta );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        SubtractAndGet(long delta)
        {
            return AddAndGet( -delta );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        IncrementAndGet()
        {
            return Interlocked.Increment( ref itsValue );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        GetAndIncrement()
        {
            return Interlocked.Increment( ref itsValue ) - 1;    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        DecrementAndGet()
        {
            return Interlocked.Decrement( ref itsValue );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        GetAndDecrement()
        {
            return Interlocked.Decrement( ref itsValue ) - 1;    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        CompareAndSet(long expected,long update)
        {
            return
                Interlocked
                    .CompareExchange(ref itsValue,expected,update) == update;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        Get()
        {
            return Interlocked.Add( ref itsValue,0 );
        }
    }
}

//  ##########################################################################
