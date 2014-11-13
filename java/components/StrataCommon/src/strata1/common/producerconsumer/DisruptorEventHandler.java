// ##########################################################################
// # File Name:	DisruptorEventHandler.java
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

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.TimeoutHandler;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public
class DisruptorEventHandler<T>
    implements 
        EventHandler<Event<T>>,
        TimeoutHandler,
        LifecycleAware
{
    private long         itsIndex;
    private long         itsCardinality;
    private IConsumer<T> itsConsumer;
    
    /************************************************************************
     * Creates a new {@code DisruptorEventHandler}. 
     *
     * @param index
     * @param cardinality
     * @param consumer
     * @param singleConsumer
     */
    protected 
    DisruptorEventHandler(
        long         index,
        long         cardinality,
        IConsumer<T> consumer)
    {
        itsIndex       = index;
        itsCardinality = cardinality;
        itsConsumer    = consumer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onEvent(
        Event<T> event,
        long     sequence,
        boolean  endOfBatch)
        throws   Exception
    {   
        try
        {
            if ( mustConsume( event,sequence ) )
                itsConsumer.consume( event.getPayload() );
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onStart()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onShutdown()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onTimeout(long sequence) throws Exception
    {
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IConsumer<T>
    getConsumer()
    {
        return itsConsumer;
    }
    
    /************************************************************************
     *  
     *
     * @param event
     * @param sequence
     * @return
     */
    protected boolean
    mustConsume(Event<T> event,long sequence)
    {
        ISelector<T> selector = getConsumer().getSelector();

        if ( event.getKind() == DispatchKind.ROUTE )
            return
                selector.match( event.getPayload() ) &&
                itsIndex == (sequence % itsCardinality);
        else
            return selector.match(event.getPayload());
    }
     
    /************************************************************************
     *  
     *
     * @return
     */
    @SuppressWarnings("unused")
    private String
    getThreadName()
    {
        return Thread.currentThread().getName() + ": ";
    }
}

// ##########################################################################
