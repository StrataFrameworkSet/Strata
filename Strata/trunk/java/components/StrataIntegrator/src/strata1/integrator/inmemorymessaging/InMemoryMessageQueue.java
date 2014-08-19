// ##########################################################################
// # File Name:	InMemoryMessageQueue.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.inmemorymessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.ISelector;
import strata1.common.utility.ISynchronizer;
import strata1.common.utility.ReadWriteLockSynchronizer;
import java.util.LinkedList;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class InMemoryMessageQueue
{
    private final ISynchronizer        itsSynchronizer;
    private final LinkedList<IMessage> itsImp;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageQueue}. 
     *
     */ 
    InMemoryMessageQueue()
    {
        itsSynchronizer = new ReadWriteLockSynchronizer();
        itsImp          = new LinkedList<IMessage>();
    }

    /************************************************************************
     *  
     *
     * @param message
     */
    void
    put(IMessage message)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            itsImp.addLast( message );
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    IMessage
    take(ISelector selector)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            for (IMessage message : itsImp)
            {              
                if ( selector.evaluate(message) )
                {
                    itsImp.remove( message );
                    return message;
                }
            }
            
            return null;           
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }

}

// ##########################################################################
