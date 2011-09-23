// ##########################################################################
// # File Name:	SpringComponentManager.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.springplatform;


import strata1.common.platform.ComponentManager;

import org.springframework.context.*;

/**
 * {@code ComponentManager} adapter for 
 * Spring {@code ApplicationContext}s.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringComponentManager
	implements ComponentManager
{
	private ConfigurableApplicationContext itsContext;
	
	/************************************************************************
	 * Creates a new {@code SpringComponentManager}. 
	 *
	 * @param context
	 */
	public 
	SpringComponentManager(ConfigurableApplicationContext context)
	{
		super();
		itsContext = context;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	open()
	{
		itsContext.start();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	refresh()
	{
		itsContext.refresh();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	close()
	{
		itsContext.stop();
		itsContext.close();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public 
	<T> T 
	getComponent(Class<T> componentClass,String componentName)
	{
		return componentClass.cast( itsContext.getBean( componentName ) );
	}


}


// ##########################################################################
