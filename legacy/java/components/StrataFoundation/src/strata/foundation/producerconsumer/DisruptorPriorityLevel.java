// ##########################################################################
// # File Name:	DisruptorPriorityLevel.java
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

package strata.foundation.producerconsumer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DisruptorPriorityLevel<T>
{
    private final RingBuffer<T>     itsBuffer;
    private final SequenceBarrier   itsBarrier;
    private final Sequence          itsSequence;

    /************************************************************************
     * Creates a new {@code DisruptorPriorityLevel}. 
     *
     */
    public 
    DisruptorPriorityLevel(RingBuffer<T> buffer)
    {
        itsBuffer   = buffer;
        itsBarrier  = itsBuffer.newBarrier();
        itsSequence = new Sequence();
        
        itsBuffer.addGatingSequences( itsSequence );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public RingBuffer<T>
    getBuffer()
    {
        return itsBuffer;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public SequenceBarrier
    getBarrier()
    {
        return itsBarrier;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Sequence
    getSequence()
    {
        return itsSequence;
    }
}

// ##########################################################################
