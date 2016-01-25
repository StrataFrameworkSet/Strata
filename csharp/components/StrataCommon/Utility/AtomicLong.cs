//  ##########################################################################
//  # File Name: AtomicLong.cs
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
    class AtomicLong
    {
        private long value;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        AtomicLong(long val)
        {
            value = val;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        AddAndGet(long delta)
        {
            return Interlocked.Add( ref value,delta );    
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
            return Interlocked.Increment( ref value );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        GetAndIncrement()
        {
            return Interlocked.Increment( ref value ) - 1;    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        DecrementAndGet()
        {
            return Interlocked.Decrement( ref value );    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        GetAndDecrement()
        {
            return Interlocked.Decrement( ref value ) - 1;    
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
                    .CompareExchange(ref value,expected,update) == update;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public long
        Get()
        {
            return Interlocked.Add( ref value,0 );
        }
    }
}

//  ##########################################################################
