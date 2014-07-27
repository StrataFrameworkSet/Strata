// ##########################################################################
// # File Name:	GuiceAdapterModule.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.injector.guicecontainer;

import com.google.inject.AbstractModule;
import java.util.Queue;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceAdapterModule
    extends AbstractModule
{
    private Queue<GuiceBindingPair<?>> itsPending;
    
    /************************************************************************
     * Creates a new {@code GuiceAdapterModule}. 
     *
     */
    public 
    GuiceAdapterModule()
    {
        itsPending = null;
    }
    
    /************************************************************************
     * Creates a new {@code GuiceAdapterModule}. 
     *
     * @param module
     */
    public 
    GuiceAdapterModule(Queue<GuiceBindingPair<?>> pending)
    {
        itsPending = pending;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    configure()
    {
        while (!itsPending.isEmpty())
        {
            GuiceBindingPair<?>    pair    = itsPending.remove();
            
            pair.setBinder(binder());
            pair.process();
        }
    }
}

// ##########################################################################
