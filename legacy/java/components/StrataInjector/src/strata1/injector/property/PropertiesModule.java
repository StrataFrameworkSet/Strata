// ##########################################################################
// # File Name:	PropertiesModule.java
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

package strata1.injector.property;

import strata1.injector.container.AbstractModule;
import strata1.injector.container.IContainer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PropertiesModule
    extends AbstractModule
{
    private Properties itsProperties;
    
    /************************************************************************
     * Creates a new {@code PropertiesModule}. 
     *
     * @param properties
     * 
     */
    public 
    PropertiesModule(Properties properties) 
    {
        super( "PropertiesModule" );
        itsProperties = properties;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initialize(IContainer container)
    {
        for (Object i : itsProperties.keySet())
        {
            String key = (String)i;
            
            container
                .insertBinding( 
                    bindType(String.class)
                        .withKey( key )
                        .toInstance( itsProperties.getProperty( key ) ) );
        }
    }

}

// ##########################################################################
