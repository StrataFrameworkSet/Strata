// ##########################################################################
// # File Name:	ProducerConsumerManager.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ProducerConsumerManager<T>
    implements IProducerConsumerManager<T>
{
    private IBlockingCollection<T> itsChannel;
    private Set<IProducer<T>>      itsProducers;
    private IConsumer<T>           itsConsumer;
    
    /************************************************************************
     * Creates a new {@code ProducerConsumerManager}. 
     *
     */
    public 
    ProducerConsumerManager()
    {
        itsChannel   = new BlockingQueue<T>();
        itsProducers = new HashSet<IProducer<T>>();
    }

    /************************************************************************
     * Creates a new {@code ProducerConsumerManager}. 
     *
     * @param channel
     */
    public
    ProducerConsumerManager(IBlockingCollection<T> channel)
    {
        itsChannel   = channel;
        itsProducers = new HashSet<IProducer<T>>();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    attachProducer(IProducer<T> producer)
    {
        if ( producer == null )
            return;
        
        itsProducers.add( producer );
        producer.setSink( itsChannel );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    attachConsumer(IConsumer<T> consumer)
    {
        if ( consumer == null )
            return;
        
        itsConsumer = consumer;
        itsConsumer.setSource( itsChannel );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    detachProducer(IProducer<T> producer)
    {
        if ( hasProducer( producer ))
        {
            itsProducers.remove( producer );
            producer.clearSink();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConsumer<T> 
    detachConsumer()
    {
        IConsumer<T> consumer = itsConsumer;
        
        itsConsumer = null;
        
        if ( consumer != null )
            consumer.clearSource();
        
        return consumer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<IProducer<T>> 
    getProducers()
    {
        return Collections.unmodifiableSet( itsProducers );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConsumer<T> 
    getConsumer()
    {
        return itsConsumer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasProducer(IProducer<T> producer)
    {
        return itsProducers.contains( producer );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(IConsumer<T> consumer)
    {
        return itsConsumer == consumer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProducers()
    {
        for (IProducer<T> producer : itsProducers)
            producer.startProducing();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startConsumer()
    {
        if ( itsConsumer == null )
            throw new IllegalStateException("consumer is null");
        
        itsConsumer.startConsuming();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopProducers()
    {
        for (IProducer<T> producer : itsProducers)
            producer.stopProducing();        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopConsumer()
    {
        if ( itsConsumer == null )
            throw new IllegalStateException("consumer is null");
        
        itsConsumer.stopConsuming();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startUp()
    {
        startProducers();
        startConsumer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    shutDown()
    {
        stopProducers();
        stopConsumer();
    }

}

// ##########################################################################
