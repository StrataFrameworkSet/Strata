// ##########################################################################
// # File Name:	IProducerConsumerManager.java
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

import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IProducerConsumerManager<T>
{
    public void
    attachProducer(IProducer<T> producer);
    
    public void
    attachConsumer(IConsumer<T> consumer);
    
    public void
    detachProducer(IProducer<T> producer);
    
    public IConsumer<T>
    detachConsumer();
    
    public Set<IProducer<T>>
    getProducers();
    
    public IConsumer<T>
    getConsumer();
    
    public boolean
    hasProducer(IProducer<T> producer);
    
    public boolean
    hasConsumer(IConsumer<T> consumer);
    
    public void
    startProducers();
    
    public void
    startConsumer();
    
    public void
    stopProducers();
    
    public void
    stopConsumer();
    
    public void
    startUp();
    
    public void
    shutDown();
} 

// ##########################################################################
