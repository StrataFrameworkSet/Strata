// ##########################################################################
// # File Name:	SkippingWaitStrategy.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.producerconsumer;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SkippingWaitStrategy
    implements WaitStrategy
{

    /************************************************************************
     * Creates a new {@code SkippingWaitStrategy}. 
     *
     */
    public 
    SkippingWaitStrategy()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    waitFor(
        long            sequence,
        Sequence        cursor,
        Sequence        dependentSequence,
        SequenceBarrier barrier)
        throws AlertException,InterruptedException,TimeoutException
    {
        long availableSequence = dependentSequence.get();
        
        if ((availableSequence) < sequence)
            barrier.checkAlert();
       
        return availableSequence;    
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    signalAllWhenBlocking()
    {
    }

}

// ##########################################################################
