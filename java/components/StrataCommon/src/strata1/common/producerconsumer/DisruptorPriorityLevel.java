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

package strata1.common.producerconsumer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.Sequencer;
import java.util.List;

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
    private final RingBuffer<Event<T>> itsBuffer;
    private final Sequence             itsSequence;
    private final SequenceBarrier      itsBarrier;
    
    private final static Sequence[]    EMPTY = new Sequence[0];

    /************************************************************************
     * Creates a new {@code DisruptorPriorityLevel}. 
     *
     */
    public 
    DisruptorPriorityLevel(RingBuffer<Event<T>> buffer)
    {
        itsBuffer   = buffer;
        itsSequence = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);
        itsBarrier  = itsBuffer.newBarrier( EMPTY );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public RingBuffer<Event<T>>
    getBuffer()
    {
        return itsBuffer;
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
}

// ##########################################################################
