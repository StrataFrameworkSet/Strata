// ##########################################################################
// # File Name:	CountRequestProducer.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommonTest Framework.
// #
// #   			The StrataCommonTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommonTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommonTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.producerconsumer;

import java.util.concurrent.atomic.AtomicInteger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CountRequestPriorityProducer
    extends    AbstractActivePriorityProducer<CountRequest>
    implements ICountRequestPriorityProducer
{
    private final int           itsTypeId;
    private final int           itsMaxCount;
    private final DispatchKind  itsKind;
    private final AtomicInteger itsCurrentCount;
    
    /************************************************************************
     * Creates a new {@code CountRequestProducer}. 
     *
     */
    public 
    CountRequestPriorityProducer(int typeId,int max,DispatchKind kind)
    {
        itsTypeId       = typeId;
        itsMaxCount     = max;
        itsKind         = kind;
        itsCurrentCount = new AtomicInteger(0);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getCount()
    {
        return itsCurrentCount.get();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected Runnable 
    getRunnable()
    {
        return 
            new Runnable()
            {
                @Override
                public void 
                run()
                {
                    for (int i=0;i<itsMaxCount;i++)
                    {
                        itsCurrentCount.incrementAndGet();
                        
                        if ( itsKind == DispatchKind.ROUTE )
                            getPriorityDispatcher().route( 1,new CountRequest(itsTypeId));
                        else
                            getPriorityDispatcher().broadcast( 1,new CountRequest(itsTypeId));
                    }
                    
                    System
                        .out
                        .println( 
                            "debug: Producer " + itsTypeId + " " +
                            "dispatched all " + itsMaxCount + " requests");
                }
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    stopRunnable()
    {
        if ( isProducing() )
            while ( itsCurrentCount.get() < itsMaxCount )
                try
                {
                    Thread.sleep( 100 );
                }
                catch(InterruptedException e) {}
    }

}

// ##########################################################################
