// ##########################################################################
// # File Name:	Coordinator.java
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
class Coordinator<
    T,
    P extends IProducer<T,C,R,S>,
    C extends IConsumer<T,C,R,S>,
    R extends IRouter<T,C,R,S>,
    S extends ISelector<T>>
    implements ICoordinator<T,P,C,R,S>
{
    private Set<P> itsProducers;
    private R      itsRouter;  
    private Set<C> itsConsumers;
    
    /************************************************************************
     * Creates a new {@code Coordinator}. 
     *
     */
    public 
    Coordinator()
    {
        itsProducers = new HashSet<P>();
        itsConsumers = new HashSet<C>();
    }

    /************************************************************************
     * Creates a new {@code Coordinator}. 
     *
     * @param channel
     */
    public
    Coordinator(R router)
    {
        this();
        itsRouter = router;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Coordinator<T,P,C,R,S> 
    attachProducer(P producer)
    {
        if ( producer != null )
        {
            itsProducers.add( producer );
            producer.setSink( itsRouter );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Coordinator<T,P,C,R,S> 
    attachConsumer(C consumer)
    {
        if ( consumer != null )
        {
            itsConsumers.add( consumer );
            consumer.setSource( itsRouter );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Coordinator<T,P,C,R,S> 
    detachProducer(P producer)
    {
        if ( hasProducer( producer ))
        {
            itsProducers.remove( producer );
            producer.clearSink();
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Coordinator<T,P,C,R,S> 
    detachConsumer(C consumer)
    {
        if ( hasConsumer( consumer ))
        {
            itsConsumers.remove( consumer );
            consumer.clearSource();
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<P> 
    getProducers()
    {
        return Collections.unmodifiableSet( itsProducers );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<C> 
    getConsumers()
    {
        return Collections.unmodifiableSet( itsConsumers );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasProducer(P producer)
    {
        return itsProducers.contains( producer );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(C consumer)
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
        for (P producer : itsProducers)
            producer.startProducing();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startConsumers()
    {
        for (C consumer : itsConsumers)
            consumer.startConsuming();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopProducers()
    {
        for (P producer : itsProducers)
            producer.stopProducing();        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopConsumers()
    {
        for (C consumer : itsConsumers)
            consumer.stopConsuming();
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
