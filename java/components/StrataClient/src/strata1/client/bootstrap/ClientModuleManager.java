// ##########################################################################
// # File Name:	ClientModuleManager.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.bootstrap;

import strata1.common.logger.LoggingLevel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ClientModuleManager
    implements IClientModuleManager
{
    private Map<String,IClientModule> itsModules;
    
    /************************************************************************
     * Creates a new {@code ClientModuleManager}. 
     *
     */
    public 
    ClientModuleManager()
    {
        itsModules = new HashMap<String,IClientModule>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Iterator<IClientModule> 
    iterator()
    {
        return itsModules.values().iterator();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerModule(IClientModule module)
    {
        itsModules.put( module.getClass().getName(),module );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getNumberOfModules()
    {
        return itsModules.size();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initialize(IClientBootstrapper bootstrapper)
    {
        IStartUpController controller = bootstrapper.getController();
        
        for (IClientModule module:itsModules.values())
        {
            String message = 
                "Initializing module: " +
                module.getClass().getSimpleName();
            
            bootstrapper
                .getLogger()
                .log( LoggingLevel.INFO,message );
            
            controller.setInitializationMessage( message );
            controller.incrementInitializationProgress();
           
            bootstrapper
                .getDispatcher()
                .processWork();
            
            module.initialize( bootstrapper );  
            
        }
    }

}

// ##########################################################################
