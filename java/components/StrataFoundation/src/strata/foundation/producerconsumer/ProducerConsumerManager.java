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

package strata.foundation.producerconsumer;

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
    private Set<IProducer<T>> itsProducers;
    private IDispatcher<T>        itsRouter;  
    private Set<IConsumer<T>> itsConsumers;
    
    /************************************************************************
     * Creates a new {@code ProducerConsumerManager}. 
     *
     */
    public 
    ProducerConsumerManager()
    {
        itsProducers = new HashSet<IProducer<T>>();
        itsConsumers = new HashSet<IConsumer<T>>();
    }

    /************************************************************************
     * Creates a new {@code ProducerConsumerManager}. 
     *
     * @param channel
     */
    public
    ProducerConsumerManager(IDispatcher<T> router)
    {
        this();
        itsRouter = router;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ProducerConsumerManager<T>
    attachProducer(IProducer<T> producer)
    {
        if ( producer != null )
        {
            itsProducers.add( producer );
            producer.setDispatcher( itsRouter );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ProducerConsumerManager<T> 
    attachConsumer(IConsumer<T> consumer)
    {
        if ( consumer != null )
        {
            itsConsumers.add( consumer );
            itsRouter.attachConsumer( consumer );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ProducerConsumerManager<T> 
    detachProducer(IProducer<T> producer)
    {
        if ( hasProducer( producer ))
        {
            itsProducers.remove( producer );
            producer.clearDispatcher();
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ProducerConsumerManager<T> 
    detachConsumer(IConsumer<T> consumer)
    {
        if ( hasConsumer( consumer ))
        {
            itsConsumers.remove( consumer );
            itsRouter.detachConsumer( consumer );
        }
        
        return this;
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
    public Set<IConsumer<T>> 
    getConsumers()
    {
        return Collections.unmodifiableSet( itsConsumers );
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
        return itsConsumers.contains( consumer );
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
    startConsumers()
    {
        for (IConsumer<T> consumer : itsConsumers);
            //consumer.startConsuming();
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
    stopConsumers()
    {
        for (IConsumer<T> consumer : itsConsumers);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startUp()
    {
        startProducers();
        startConsumers();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    shutDown()
    {
        stopProducers();
        stopConsumers();
    }

}

// ##########################################################################
